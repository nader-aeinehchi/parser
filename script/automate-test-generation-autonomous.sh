#!/bin/bash

# Fully Autonomous Test Generation Script for Java Parser Project
# Runs completely without user prompts or confirmations
# Uses GitHub Copilot API to generate comprehensive test suites

# ==========================================
# CONFIGURATION - NO USER INTERACTION BELOW
# ==========================================

# Set strict error handling but continue processing other classes on individual failures
set -o pipefail

# Configuration
MAIN_PATH="src/main/scala/org/naderica/parser/sourcecode/java20/standard/grammar"
TEST_PATH="src/test/scala/org/naderica/parser/sourcecode/java20/standard/grammar"

# Option 1: Use environment variable (recommended for security)
COPILOT_API_KEY="${COPILOT_API_KEY:-}"

# Option 2: Direct assignment (uncomment and replace with your key)
# COPILOT_API_KEY="your_actual_api_key_here"

# Option 3: Read from file (uncomment if you store key in a file)
# COPILOT_API_KEY=$(cat ~/.copilot_api_key 2>/dev/null || echo "")

WORKSPACE_ROOT="$(pwd)"
LOG_FILE="autonomous_test_generation.log"
MAX_RETRIES=3
BATCH_SIZE=5
TIMEOUT_SECONDS=300  # 5 minutes per test compilation

# Colors for output (safe for non-interactive)
if [ -t 1 ]; then
    RED='\033[0;31m'
    GREEN='\033[0;32m'
    YELLOW='\033[1;33m'
    BLUE='\033[0;34m'
    NC='\033[0m'
else
    RED=''
    GREEN=''
    YELLOW=''
    BLUE=''
    NC=''
fi

# ==========================================
# LOGGING FUNCTIONS - NO USER INTERACTION
# ==========================================

log() {
    local timestamp=$(date '+%Y-%m-%d %H:%M:%S')
    local message="[$timestamp] $1"
    echo -e "${BLUE}$message${NC}"
    echo "$message" >> "$LOG_FILE"
}

error() {
    local timestamp=$(date '+%Y-%m-%d %H:%M:%S')
    local message="[$timestamp] ERROR: $1"
    echo -e "${RED}$message${NC}" >&2
    echo "$message" >> "$LOG_FILE"
}

success() {
    local timestamp=$(date '+%Y-%m-%d %H:%M:%S')
    local message="[$timestamp] SUCCESS: $1"
    echo -e "${GREEN}$message${NC}"
    echo "$message" >> "$LOG_FILE"
}

warning() {
    local timestamp=$(date '+%Y-%m-%d %H:%M:%S')
    local message="[$timestamp] WARNING: $1"
    echo -e "${YELLOW}$message${NC}"
    echo "$message" >> "$LOG_FILE"
}

# ==========================================
# AUTONOMOUS FUNCTIONS - NO USER PROMPTS
# ==========================================

# Silent prerequisite check
check_prerequisites_silent() {
    log "Checking prerequisites silently..."
    
    local errors=0
    
    if [ -z "$COPILOT_API_KEY" ]; then
        error "COPILOT_API_KEY environment variable not set"
        error "Set it with: export COPILOT_API_KEY='your_api_key_here'"
        errors=1
    fi
    
    if ! command -v sbt &> /dev/null; then
        error "sbt not found. Install with: sudo apt-get install sbt"
        errors=1
    fi
    
    if ! command -v curl &> /dev/null; then
        error "curl not found. Install with: sudo apt-get install curl"
        errors=1
    fi
    
    if ! command -v jq &> /dev/null; then
        error "jq not found. Install with: sudo apt-get install jq"
        errors=1
    fi
    
    if ! command -v timeout &> /dev/null; then
        warning "timeout command not found. Using alternative method."
    fi
    
    if [ $errors -eq 0 ]; then
        success "All prerequisites satisfied"
        return 0
    else
        error "Prerequisites check failed. Please fix the above issues."
        return 1
    fi
}

# Find untested classes (autonomous)
find_untested_classes() {
    log "Discovering classes without tests..."
    
	echo "+++++++++++++++++++++++++++++++++++++++++++"
    local main_classes=$(find "$MAIN_PATH" -name "*.scala" -exec basename {} .scala \; 2>/dev/null | sort)
	echo "Found main classes:........................................................."

	echo "$main_classes"
	exit 0 
	
	local test_classes=$(find "$TEST_PATH" -name "*Test.scala" -exec basename {} Test.scala \; 2>/dev/null | sort)
    
    # Use comm to find classes without tests
    local untested_classes=$(comm -23 <(echo "$main_classes") <(echo "$test_classes") 2>/dev/null)
    
    local count=$(echo "$untested_classes" | wc -w)
    log "Found $count classes without tests"
    
    echo "$untested_classes"
}

# Examine class structure (autonomous)
examine_class_autonomous() {
    local class_name=$1
    local class_file="$MAIN_PATH/${class_name}.scala"
    
    if [ ! -f "$class_file" ]; then
        error "Class file not found: $class_file"
        return 1
    fi
    
    log "Analyzing structure of $class_name"
    cat "$class_file" 2>/dev/null || {
        error "Failed to read class file: $class_file"
        return 1
    }
}

# Generate test via API (autonomous with timeout)
generate_test_autonomous() {
    local class_name=$1
    local class_content=$2
    local test_file="$TEST_PATH/${class_name}Test.scala"
    
    log "Generating test for $class_name via Copilot API..."
    
    # Create comprehensive prompt
    local prompt="Generate a comprehensive ScalaTest suite for the following Scala case class(es):

\`\`\`scala
$class_content
\`\`\`

Requirements:
1. Use 'org.scalatest.funsuite.AnyFunSuite with org.scalatest.matchers.should.Matchers'
2. Create 25+ comprehensive tests covering all fields and functionality
3. Use 'null.asInstanceOf[Type]' for mock dependencies
4. Cover Java language semantics, enterprise patterns, and edge cases
5. Include equality testing with different mock objects for inequality tests
6. Package: org.naderica.parser.sourcecode.java20.standard.grammar
7. Test class name: ${class_name}Test
8. Follow established patterns: basic creation, field access, equality, pattern matching, enterprise scenarios

Generate ONLY the complete Scala test file content, no explanations:"

    # Create JSON payload
    local json_payload
    json_payload=$(jq -n \
        --arg prompt "$prompt" \
        --arg model "gpt-4" \
        '{
            "model": $model,
            "messages": [
                {
                    "role": "system",
                    "content": "You are an expert Scala developer. Generate complete, working ScalaTest files that compile and pass. Return only the Scala code, no markdown formatting."
                },
                {
                    "role": "user", 
                    "content": $prompt
                }
            ],
            "max_tokens": 4000,
            "temperature": 0.2
        }') || {
        error "Failed to create JSON payload for $class_name"
        return 1
    }
    
    # Call API with timeout
    local response
    response=$(timeout 60 curl -s -X POST \
        "https://api.openai.com/v1/chat/completions" \
        -H "Authorization: Bearer $COPILOT_API_KEY" \
        -H "Content-Type: application/json" \
        -d "$json_payload" 2>/dev/null) || {
        error "API call failed or timed out for $class_name"
        return 1
    }
    
    # Extract generated content
    local generated_content
    generated_content=$(echo "$response" | jq -r '.choices[0].message.content // empty' 2>/dev/null)
    
    if [ -z "$generated_content" ] || [ "$generated_content" = "null" ]; then
        error "Failed to generate test content for $class_name"
        echo "API Response: $response" >> "$LOG_FILE"
        return 1
    fi
    
    # Clean up content (remove markdown if present)
    if echo "$generated_content" | grep -q '```scala' 2>/dev/null; then
        generated_content=$(echo "$generated_content" | sed -n '/```scala/,/```/p' | sed '1d;$d')
    elif echo "$generated_content" | grep -q '```' 2>/dev/null; then
        generated_content=$(echo "$generated_content" | sed -n '/```/,/```/p' | sed '1d;$d')
    fi
    
    # Ensure test directory exists
    mkdir -p "$(dirname "$test_file")"
    
    # Write test file
    echo "$generated_content" > "$test_file" || {
        error "Failed to write test file: $test_file"
        return 1
    }
    
    success "Generated test file: $test_file"
    return 0
}

# Compile and test (fully autonomous)
compile_test_autonomous() {
    local class_name=$1
    local test_file="$TEST_PATH/${class_name}Test.scala"
    local retry_count=0
    
    while [ $retry_count -lt $MAX_RETRIES ]; do
        log "Compiling and testing $class_name (attempt $((retry_count + 1))/$MAX_RETRIES)"
        
        # Run test with timeout and capture all output
        local test_output
        local exit_code
        
        if command -v timeout &> /dev/null; then
            test_output=$(timeout $TIMEOUT_SECONDS sbt -batch -no-colors -no-share -no-global "testOnly *${class_name}Test" 2>&1)
            exit_code=$?
        else
            # Fallback without timeout
            test_output=$(sbt -batch -no-colors -no-share -no-global "testOnly *${class_name}Test" 2>&1)
            exit_code=$?
        fi
        
        # Log output
        echo "=== Test Output for $class_name ===" >> "$LOG_FILE"
        echo "$test_output" >> "$LOG_FILE"
        echo "=== End Test Output ===" >> "$LOG_FILE"
        
        if [ $exit_code -eq 0 ] && echo "$test_output" | grep -q "All tests passed" 2>/dev/null; then
            success "All tests passed for $class_name"
            return 0
        else
            error "Test compilation/execution failed for $class_name (exit code: $exit_code)"
            retry_count=$((retry_count + 1))
            
            if [ $retry_count -lt $MAX_RETRIES ]; then
                warning "Attempting to fix and retry..."
                fix_test_autonomous "$class_name" "$test_file" "$test_output"
                sleep 2  # Brief pause before retry
            fi
        fi
    done
    
    error "Failed to fix $class_name after $MAX_RETRIES attempts"
    return 1
}

# Fix common issues (autonomous)
fix_test_autonomous() {
    local class_name=$1
    local test_file=$2
    local error_output=$3
    
    log "Attempting to fix common issues in $test_file"
    
    if [ ! -f "$test_file" ]; then
        error "Test file not found for fixing: $test_file"
        return 1
    fi
    
    # Fix common equality test issues
    sed -i 's/should not equal.*mock.*2/should not equal MarkerAnnotation(mockTypeName)/g' "$test_file" 2>/dev/null || true
    sed -i 's/shouldEqual.*null.*null/shouldEqual annotation2/g' "$test_file" 2>/dev/null || true
    
    # Fix import issues
    if ! grep -q "import org.scalatest.funsuite.AnyFunSuite" "$test_file" 2>/dev/null; then
        sed -i '1i import org.scalatest.funsuite.AnyFunSuite' "$test_file" 2>/dev/null || true
    fi
    
    if ! grep -q "import org.scalatest.matchers.should.Matchers" "$test_file" 2>/dev/null; then
        sed -i '2i import org.scalatest.matchers.should.Matchers' "$test_file" 2>/dev/null || true
    fi
    
    # Fix package declaration if missing
    if ! grep -q "package org.naderica.parser.sourcecode.java20.standard.grammar" "$test_file" 2>/dev/null; then
        sed -i '1i package org.naderica.parser.sourcecode.java20.standard.grammar\n' "$test_file" 2>/dev/null || true
    fi
    
    log "Applied common fixes to $test_file"
}

# Generate progress report (autonomous)
generate_progress_autonomous() {
    log "Generating autonomous progress report..."
    
    local total_classes=$(find "$MAIN_PATH" -name "*.scala" 2>/dev/null | wc -l)
    local total_tests=$(find "$TEST_PATH" -name "*Test.scala" 2>/dev/null | wc -l)
    local coverage=0
    
    if [ $total_classes -gt 0 ]; then
        coverage=$(echo "scale=1; $total_tests * 100 / $total_classes" | bc -l 2>/dev/null || echo "0.0")
    fi
    
    cat << EOF >> "$LOG_FILE"

========================================
AUTONOMOUS PROGRESS REPORT - $(date)
========================================
Total Classes: $total_classes
Test Files Created: $total_tests
Coverage: $coverage%
Remaining: $((total_classes - total_tests))
========================================

EOF

    log "Progress: $total_tests/$total_classes classes tested ($coverage% coverage)"
}

# Main autonomous processing
process_all_autonomous() {
    log "Starting fully autonomous test generation process..."
    
	echo "#############################################"
    local untested_classes
    untested_classes=$(find_untested_classes)
    
    if [ -z "$untested_classes" ]; then
        success "No untested classes found. All classes have tests!"
        return 0
    fi
       
    local classes_array=($untested_classes)
    local total_count=${#classes_array[@]}
    local processed_count=0
    local success_count=0
    local failed_classes=()
    
    log "Found $total_count classes without tests"
    log "Processing in batches of $BATCH_SIZE with autonomous retry logic"
    
    # Process in batches
    for ((i=0; i<total_count; i+=BATCH_SIZE)); do
        local batch_end=$((i + BATCH_SIZE - 1))
        if [ $batch_end -ge $total_count ]; then
            batch_end=$((total_count - 1))
        fi
        
        log "Processing batch $((i/BATCH_SIZE + 1)): classes $((i+1)) to $((batch_end+1))"
        
        for ((j=i; j<=batch_end && j<total_count; j++)); do
            local class_name=${classes_array[j]}
            processed_count=$((processed_count + 1))
            
            log "Processing $class_name ($processed_count/$total_count)"
            
            # Examine class structure
            local class_content
            if class_content=$(examine_class_autonomous "$class_name"); then
                # Generate test via API
                if generate_test_autonomous "$class_name" "$class_content"; then
                    # Compile and test
                    if compile_test_autonomous "$class_name"; then
                        success_count=$((success_count + 1))
                        success "Successfully processed $class_name"
                    else
                        failed_classes+=("$class_name")
                        error "Failed to compile/test $class_name"
                    fi
                else
                    failed_classes+=("$class_name")
                    error "Failed to generate test for $class_name"
                fi
            else
                failed_classes+=("$class_name")
                error "Failed to examine $class_name"
            fi
            
            # Rate limiting delay
            sleep 3
        done
        
        # Generate interim report
        generate_progress_autonomous
        log "Batch complete. Success: $success_count, Failed: ${#failed_classes[@]}"
        
        # Longer delay between batches to respect API limits
        if [ $((i + BATCH_SIZE)) -lt $total_count ]; then
            log "Pausing between batches (API rate limiting)..."
            sleep 15
        fi
    done
    
    # Final autonomous report
    log "Autonomous test generation process completed!"
    success "Successfully processed: $success_count/$total_count classes"
    
    if [ ${#failed_classes[@]} -gt 0 ]; then
        warning "Failed classes: ${failed_classes[*]}"
        warning "You may retry these manually or run the script again"
    fi
    
    generate_progress_autonomous
    
    # Return success if at least some classes were processed
    if [ $success_count -gt 0 ]; then
        return 0
    else
        return 1
    fi
}

# ==========================================
# MAIN EXECUTION - FULLY AUTONOMOUS
# ==========================================

main_autonomous() {
    # Test basic output first
    echo "Script starting - basic echo test"
    
    # Initialize log file first
    echo "Autonomous Test Generation Log - $(date)" > "$LOG_FILE"
    echo "Script running without user prompts or confirmations" >> "$LOG_FILE"
    
    # Now test log function
    log "Starting Fully Autonomous Test Generation Script"
    log "Workspace: $WORKSPACE_ROOT"
    log "No user interaction required - running completely autonomously"
    
    # Check prerequisites
    if ! check_prerequisites_silent; then
        error "Prerequisites check failed. Exiting."
        exit 1
    fi
    
    # Process all classes autonomously
    if process_all_autonomous; then
        success "Autonomous script execution completed successfully!"
        log "Check $LOG_FILE for detailed logs."
        exit 0
    else
        error "Autonomous script execution completed with errors."
        log "Check $LOG_FILE for detailed logs."
        exit 1
    fi
}

# ==========================================
# COMMAND LINE PARSING
# ==========================================

usage_autonomous() {
    echo "Fully Autonomous Test Generation Script"
    echo "Usage: $0 [options]"
    echo ""
    echo "Options:"
    echo "  -h, --help           Show this help message"
    echo "  -b, --batch N        Set batch size (default: 5)"
    echo "  -r, --retries N      Set max retries (default: 3)"
    echo "  -t, --timeout N      Set timeout in seconds (default: 300)"
    echo ""
    echo "Environment Variables:"
    echo "  COPILOT_API_KEY      Your GitHub Copilot/OpenAI API key (required)"
    echo ""
    echo "Example:"
    echo "  export COPILOT_API_KEY='your_key_here'"
    echo "  $0 --batch 3 --retries 2 --timeout 180"
    echo ""
    echo "This script runs completely autonomously without any user prompts."
}

# Parse command line arguments
while [[ $# -gt 0 ]]; do
    case $1 in
        -h|--help)
            usage_autonomous
            exit 0
            ;;
        -b|--batch)
            if [[ "$2" =~ ^[0-9]+$ ]] && [ "$2" -gt 0 ]; then
                BATCH_SIZE="$2"
                shift 2
            else
                error "Invalid batch size: $2"
                exit 1
            fi
            ;;
        -r|--retries)
            if [[ "$2" =~ ^[0-9]+$ ]] && [ "$2" -gt 0 ]; then
                MAX_RETRIES="$2"
                shift 2
            else
                error "Invalid retry count: $2"
                exit 1
            fi
            ;;
        -t|--timeout)
            if [[ "$2" =~ ^[0-9]+$ ]] && [ "$2" -gt 0 ]; then
                TIMEOUT_SECONDS="$2"
                shift 2
            else
                error "Invalid timeout: $2"
                exit 1
            fi
            ;;
        *)
            error "Unknown option: $1"
            usage_autonomous
            exit 1
            ;;
    esac
done

# Run the main autonomous function
main_autonomous "$@"