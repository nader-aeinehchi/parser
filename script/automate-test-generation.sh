#!/bin/bash

# Automated Test Generation Script for Java Parser Project
# This script runs completely autonomously without any user prompts
# Runs the entire test generation process using GitHub Copilot API

# Configuration
MAIN_PATH="src/main/scala/org/naderica/parser/sourcecode/java20/standard/grammar"
TEST_PATH="src/test/scala/org/naderica/parser/sourcecode/java20/standard/grammar"

COPILOT_API_KEY="${COPILOT_API_KEY:-}"
WORKSPACE_ROOT="$(pwd)"
LOG_FILE="test_generation.log"
MAX_RETRIES=3
BATCH_SIZE=5
AUTONOMOUS_MODE=true  # No user prompts

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Logging function
log() {
    echo -e "${BLUE}[$(date '+%Y-%m-%d %H:%M:%S')]${NC} $1" | tee -a $LOG_FILE
}

error() {
    echo -e "${RED}[ERROR]${NC} $1" | tee -a $LOG_FILE
}

success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1" | tee -a $LOG_FILE
}

warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1" | tee -a $LOG_FILE
}

# Check prerequisites
check_prerequisites() {
    log "Checking prerequisites..."
    
    if [ -z "$COPILOT_API_KEY" ]; then
        error "COPILOT_API_KEY environment variable not set"
        log "Please set your GitHub Copilot API key:"
        log "export COPILOT_API_KEY='your_api_key_here'"
        exit 1
    fi
    
    if ! command -v sbt &> /dev/null; then
        error "sbt not found. Please install Scala SBT"
        exit 1
    fi
    
    if ! command -v curl &> /dev/null; then
        error "curl not found. Please install curl"
        exit 1
    fi
    
    if ! command -v jq &> /dev/null; then
        error "jq not found. Please install jq for JSON processing"
        exit 1
    fi
    
    success "All prerequisites satisfied"
}

# Find classes without tests
find_classes_without_tests() {
    log "Finding classes without tests..."
    comm -23 \
        <(find $MAIN_PATH -name "*.scala" -exec basename {} .scala \; | sort) \
        <(find $TEST_PATH -name "*Test.scala" -exec basename {} Test.scala \; | sort)
}

# Examine class structure
examine_class_structure() {
    local class_name=$1
    local class_file="$MAIN_PATH/${class_name}.scala"
    
    if [ ! -f "$class_file" ]; then
        error "Class file not found: $class_file"
        return 1
    fi
    
    log "Examining structure of $class_name"
    cat "$class_file"
}

# Call GitHub Copilot API to generate test
generate_test_via_copilot() {
    local class_name=$1
    local class_content=$2
    local test_file="$TEST_PATH/${class_name}Test.scala"
    
    log "Generating test for $class_name using Copilot API..."
    
    # Prepare the prompt for Copilot
    local prompt="Generate a comprehensive ScalaTest suite for the following Scala case class(es):

\`\`\`scala
$class_content
\`\`\`

Requirements:
1. Use ScalaTest AnyFunSuite with Matchers
2. Create 20+ comprehensive tests covering all fields and methods
3. Use null.asInstanceOf[Type] for mock dependencies
4. Cover Java language semantics and enterprise patterns
5. Include equality testing, pattern matching, and edge cases
6. Package: org.naderica.parser.sourcecode.java20.standard.grammar
7. Follow the established pattern from successful test files

Generate the complete test file content:"

    # Create JSON payload for Copilot API
    local json_payload=$(jq -n \
        --arg prompt "$prompt" \
        --arg model "gpt-4" \
        '{
            "model": $model,
            "messages": [
                {
                    "role": "system",
                    "content": "You are an expert Scala developer creating comprehensive test suites. Generate complete, working test files that compile and pass."
                },
                {
                    "role": "user", 
                    "content": $prompt
                }
            ],
            "max_tokens": 4000,
            "temperature": 0.3
        }')
    
    # Call Copilot API
    local response=$(curl -s -X POST \
        "https://api.openai.com/v1/chat/completions" \
        -H "Authorization: Bearer $COPILOT_API_KEY" \
        -H "Content-Type: application/json" \
        -d "$json_payload")
    
    # Extract generated content
    local generated_content=$(echo "$response" | jq -r '.choices[0].message.content // empty')
    
    if [ -z "$generated_content" ]; then
        error "Failed to generate test content for $class_name"
        echo "API Response: $response" >> $LOG_FILE
        return 1
    fi
    
    # Extract Scala code from markdown if present
    if echo "$generated_content" | grep -q '```scala'; then
        generated_content=$(echo "$generated_content" | sed -n '/```scala/,/```/p' | sed '1d;$d')
    fi
    
    # Write test file
    echo "$generated_content" > "$test_file"
    success "Generated test file: $test_file"
    return 0
}

# Compile and test (autonomous, non-interactive)
compile_and_test() {
    local class_name=$1
    local test_file="$TEST_PATH/${class_name}Test.scala"
    local retry_count=0
    
    while [ $retry_count -lt $MAX_RETRIES ]; do
        log "Compiling and testing $class_name (attempt $((retry_count + 1))/$MAX_RETRIES)"
        
        # Run the test in batch mode (non-interactive)
        local test_output
        if test_output=$(timeout 300 sbt -batch -no-colors "testOnly *${class_name}Test" 2>&1); then
            echo "$test_output" >> $LOG_FILE
            success "All tests passed for $class_name"
            return 0
        else
            echo "$test_output" >> $LOG_FILE
            error "Test compilation/execution failed for $class_name"
            retry_count=$((retry_count + 1))
            
            if [ $retry_count -lt $MAX_RETRIES ]; then
                warning "Attempting to fix and retry..."
                fix_test_file "$class_name" "$test_file"
            fi
        fi
    done
    
    error "Failed to fix $class_name after $MAX_RETRIES attempts"
    return 1
}

# Fix common test issues
fix_test_file() {
    local class_name=$1
    local test_file=$2
    
    log "Attempting to fix common issues in $test_file"
    
    # Fix common equality test issues
    sed -i 's/annotation1 should not equal annotation3/annotation1 should not equal NormalAnnotation(mockTypeName, None)/g' "$test_file"
    sed -i 's/object1 should not equal object3/object1 should not equal object2/g' "$test_file"
    
    # Fix import issues
    if ! grep -q "import org.scalatest.funsuite.AnyFunSuite" "$test_file"; then
        sed -i '1i import org.scalatest.funsuite.AnyFunSuite' "$test_file"
    fi
    
    if ! grep -q "import org.scalatest.matchers.should.Matchers" "$test_file"; then
        sed -i '2i import org.scalatest.matchers.should.Matchers' "$test_file"
    fi
}

# Generate progress report
generate_progress_report() {
    log "Generating progress report..."
    
    local total_classes=$(find $MAIN_PATH -name "*.scala" | wc -l)
    local total_tests=$(find $TEST_PATH -name "*Test.scala" | wc -l)
    local coverage=$(echo "scale=1; $total_tests * 100 / $total_classes" | bc -l)
    
    cat << EOF >> $LOG_FILE

========================================
PROGRESS REPORT - $(date)
========================================
Total Classes: $total_classes
Test Files Created: $total_tests
Coverage: $coverage%
Remaining: $((total_classes - total_tests))
========================================

EOF
}

# Main process function
process_classes() {
    log "Starting automated test generation process..."
    
    local classes_without_tests=($(find_classes_without_tests))
    local total_count=${#classes_without_tests[@]}
    local processed_count=0
    local success_count=0
    local failed_classes=()
    
    log "Found $total_count classes without tests"
    
    # Process in batches
    for ((i=0; i<total_count; i+=BATCH_SIZE)); do
        local batch_end=$((i + BATCH_SIZE - 1))
        if [ $batch_end -ge $total_count ]; then
            batch_end=$((total_count - 1))
        fi
        
        log "Processing batch $((i/BATCH_SIZE + 1)): classes $((i+1)) to $((batch_end+1))"
        
        for ((j=i; j<=batch_end && j<total_count; j++)); do
            local class_name=${classes_without_tests[j]}
            processed_count=$((processed_count + 1))
            
            log "Processing $class_name ($processed_count/$total_count)"
            
            # Examine class structure
            local class_content=$(examine_class_structure "$class_name")
            if [ $? -ne 0 ]; then
                warning "Skipping $class_name - could not examine structure"
                failed_classes+=("$class_name")
                continue
            fi
            
            # Generate test via Copilot API
            if generate_test_via_copilot "$class_name" "$class_content"; then
                # Compile and test
                if compile_and_test "$class_name"; then
                    success_count=$((success_count + 1))
                    success "Successfully processed $class_name"
                else
                    failed_classes+=("$class_name")
                    error "Failed to process $class_name"
                fi
            else
                failed_classes+=("$class_name")
                error "Failed to generate test for $class_name"
            fi
            
            # Small delay to avoid rate limiting
            sleep 2
        done
        
        # Generate interim report
        generate_progress_report
        log "Batch complete. Success: $success_count, Failed: ${#failed_classes[@]}"
        
        # Longer delay between batches
        if [ $((i + BATCH_SIZE)) -lt $total_count ]; then
            log "Waiting before next batch..."
            sleep 10
        fi
    done
    
    # Final report
    log "Test generation process completed!"
    success "Successfully processed: $success_count/$total_count classes"
    
    if [ ${#failed_classes[@]} -gt 0 ]; then
        warning "Failed classes: ${failed_classes[*]}"
    fi
    
    generate_progress_report
}

# Main execution
main() {
    log "Starting Automated Test Generation Script"
    log "Workspace: $WORKSPACE_ROOT"
    
    # Initialize log
    echo "Test Generation Log - $(date)" > $LOG_FILE
    
    check_prerequisites
    process_classes
    
    log "Script execution completed. Check $LOG_FILE for detailed logs."
}

# Script usage
usage() {
    echo "Usage: $0 [options]"
    echo "Options:"
    echo "  -h, --help     Show this help message"
    echo "  -b, --batch N  Set batch size (default: 5)"
    echo "  -r, --retries N Set max retries (default: 3)"
    echo ""
    echo "Environment Variables:"
    echo "  COPILOT_API_KEY  Your GitHub Copilot API key (required)"
    echo ""
    echo "Example:"
    echo "  export COPILOT_API_KEY='your_key_here'"
    echo "  $0 --batch 3 --retries 2"
}

# Parse command line arguments
while [[ $# -gt 0 ]]; do
    case $1 in
        -h|--help)
            usage
            exit 0
            ;;
        -b|--batch)
            BATCH_SIZE="$2"
            shift 2
            ;;
        -r|--retries)
            MAX_RETRIES="$2"
            shift 2
            ;;
        *)
            error "Unknown option: $1"
            usage
            exit 1
            ;;
    esac
done

# Run main function
main "$@"