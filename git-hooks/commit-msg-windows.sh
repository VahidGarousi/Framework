#!C:/Program\ Files/Git/usr/bin/sh.exe

# Path to the commit message file
commit_msg_file="$1"

# Regular expression to match issue numbers starting with #
# Examples: #123, [#123]
issue_pattern="(\[#?[0-9]+\]|#?[0-9]+)"

# Read the commit message
commit_msg=$(cat "$commit_msg_file")

echo "Validating commit message..."
echo "Debug: Commit message is: '$commit_msg'"

# Validate commit message
if ! echo "$commit_msg" | grep -qE "$issue_pattern"; then
    echo "❌ Commit message validation failed!"
    echo "👉 Your commit message must include an issue number starting with # (e.g., #123 or [#123])."
    echo ""
    echo "Examples:"
    echo "  ✅ Fix bug in login flow #123"
    echo "  ✅ [#456] Refactor login module"
    echo "  ✅ Add tests for API #789"
    echo ""
    exit 1
fi

echo "✅ Commit message validation passed."