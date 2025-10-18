# GitHub Copilot API Integration Guide

## Overview
This guide shows how to integrate the automated test generation script with GitHub Copilot API to fully automate the test creation process.

## Prerequisites

### 1. GitHub Copilot API Access
- You need a GitHub Copilot subscription
- Generate an API token from GitHub settings
- The script uses OpenAI's API format (Copilot uses similar endpoints)

### 2. Required Tools
```bash
# Install required dependencies
sudo apt-get update
sudo apt-get install curl jq bc

# Verify sbt is installed
sbt --version
```

## Setup Instructions

### 1. Get API Access
```bash
# Option 1: GitHub Copilot API (if available)
# Visit: https://github.com/settings/tokens
# Create token with copilot permissions

# Option 2: OpenAI API (alternative)
# Visit: https://platform.openai.com/api-keys
# Create new API key
```

### 2. Configure Environment
```bash
# Set your API key
export COPILOT_API_KEY="your_api_key_here"

# Or add to your ~/.bashrc for persistence
echo 'export COPILOT_API_KEY="your_api_key_here"' >> ~/.bashrc
source ~/.bashrc
```

### 3. Make Script Executable
```bash
chmod +x automate-test-generation.sh
```

## Usage Examples

### Basic Usage
```bash
# Run with default settings (batch size: 5, retries: 3)
./automate-test-generation.sh
```

### Custom Configuration
```bash
# Process 3 classes at a time with 2 max retries
./automate-test-generation.sh --batch 3 --retries 2

# Show help
./automate-test-generation.sh --help
```

### Advanced Usage with Monitoring
```bash
# Run with live log monitoring
./automate-test-generation.sh --batch 5 | tee -a live_output.log

# Run in background with nohup
nohup ./automate-test-generation.sh --batch 10 > automation.log 2>&1 &
```

## API Call Structure

The script makes calls to the Copilot/OpenAI API with this structure:

```bash
curl -X POST "https://api.openai.com/v1/chat/completions" \
  -H "Authorization: Bearer $COPILOT_API_KEY" \
  -H "Content-Type: application/json" \
  -d '{
    "model": "gpt-4",
    "messages": [
      {
        "role": "system",
        "content": "You are an expert Scala developer creating comprehensive test suites..."
      },
      {
        "role": "user",
        "content": "Generate a comprehensive ScalaTest suite for: [CLASS_CONTENT]"
      }
    ],
    "max_tokens": 4000,
    "temperature": 0.3
  }'
```

## Script Features

### 1. Intelligent Processing
- **Class Discovery**: Automatically finds untested classes
- **Structure Analysis**: Examines each class before test generation
- **Batch Processing**: Handles multiple classes efficiently
- **Rate Limiting**: Includes delays to avoid API limits

### 2. Error Handling
- **Retry Logic**: Automatically retries failed generations
- **Compilation Fixes**: Attempts to fix common test issues
- **Comprehensive Logging**: Detailed logs for debugging

### 3. Progress Tracking
- **Real-time Status**: Shows progress as it runs
- **Coverage Reports**: Generates coverage statistics
- **Success Metrics**: Tracks success/failure rates

## Manual Copilot Integration (Alternative)

If you prefer to use VS Code Copilot directly:

### 1. Create Helper Script
```bash
#!/bin/bash
# copilot-helper.sh

CLASS_NAME=$1
CLASS_FILE="src/main/scala/org/naderica/parser/sourcecode/java20/standard/grammar/${CLASS_NAME}.scala"

echo "=== Class Structure for $CLASS_NAME ==="
cat "$CLASS_FILE"
echo ""
echo "=== Generate test with this prompt ==="
echo "Generate a comprehensive ScalaTest suite for the above class with:"
echo "- 20+ tests covering all functionality"
echo "- Use AnyFunSuite with Matchers"
echo "- Use null.asInstanceOf[Type] for mocks"
echo "- Cover enterprise patterns and edge cases"
```

### 2. Use with VS Code Copilot
```bash
# Generate prompt for Copilot
./copilot-helper.sh AnnotationInterfaceBody

# Copy the output and paste into VS Code
# Let Copilot generate the test
# Save as AnnotationInterfaceBodyTest.scala
```

## Monitoring and Debugging

### 1. Log Analysis
```bash
# Watch logs in real-time
tail -f test_generation.log

# Check for errors
grep "ERROR" test_generation.log

# View progress
grep "SUCCESS\|PROGRESS" test_generation.log
```

### 2. Manual Verification
```bash
# Check generated test
cat src/test/scala/org/naderica/parser/sourcecode/java20/standard/grammar/SomeClassTest.scala

# Run specific test
sbt "testOnly *SomeClassTest"

# Run all tests
sbt test
```

## Troubleshooting

### Common Issues

#### API Key Issues
```bash
# Verify API key is set
echo $COPILOT_API_KEY

# Test API access
curl -H "Authorization: Bearer $COPILOT_API_KEY" \
     "https://api.openai.com/v1/models"
```

#### Rate Limiting
```bash
# Increase delays in script
# Edit automate-test-generation.sh:
# Change: sleep 2  to  sleep 5
# Change: sleep 10 to sleep 30
```

#### Compilation Errors
```bash
# Check specific error
sbt "testOnly *FailedClassTest" 2>&1 | grep -A 10 "ERROR"

# Manual fix and retry
vim src/test/scala/.../FailedClassTest.scala
sbt "testOnly *FailedClassTest"
```

## Performance Optimization

### 1. Batch Size Tuning
```bash
# For faster processing (if API allows)
./automate-test-generation.sh --batch 10

# For more stable processing
./automate-test-generation.sh --batch 3
```

### 2. Parallel Processing (Advanced)
```bash
# Split classes into chunks and run multiple instances
# Create separate directories and run in parallel
mkdir -p parallel/{batch1,batch2,batch3}
# Run multiple script instances with different class lists
```

## Integration with CI/CD

### GitHub Actions Example
```yaml
name: Automated Test Generation
on:
  workflow_dispatch:
    inputs:
      batch_size:
        description: 'Batch size for processing'
        default: '5'

jobs:
  generate-tests:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v3
    - name: Setup Scala
      uses: olafurpg/setup-scala@v13
    - name: Generate Tests
      env:
        COPILOT_API_KEY: ${{ secrets.COPILOT_API_KEY }}
      run: |
        chmod +x automate-test-generation.sh
        ./automate-test-generation.sh --batch ${{ github.event.inputs.batch_size }}
    - name: Commit Generated Tests
      run: |
        git config --local user.email "action@github.com"
        git config --local user.name "GitHub Action"
        git add .
        git commit -m "Auto-generated tests" || exit 0
        git push
```

## Expected Results

After running the automation script, you should see:

```
✅ Successfully processed: 95/105 classes
📊 New coverage: 95.5% (169/177 classes)
🎯 Total tests generated: 3,200+ individual tests
⏱️ Processing time: ~45 minutes (depending on API speed)
```

The script will handle the entire process automatically, creating comprehensive test suites for all remaining classes!