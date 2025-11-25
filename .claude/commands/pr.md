# Create Pull Request

You are helping the user create a pull request for their uncommitted changes.

## Instructions

1. **Check for uncommitted changes:**
   - Run `git diff --stat` to see what files have changed
   - If no changes, inform the user and stop

2. **Determine branch name:**
   - Ask the user for a brief description of the changes (or infer from the changes and the previous discussion)
   - Check if there's a GitHub issue number mentioned in the conversation or provided by the user
   - Branch naming format:
     - If issue exists: `feature/{issue-number}-{kebab-case-description}` (e.g., `feature/23-add-search-functionality`)
     - If no issue: `feature/{kebab-case-description}` (e.g., `feature/add-search-functionality`)
   - Use "feature/" for new features, "fix/" for bug fixes, "refactor/" for refactoring

3. **Create and checkout branch:**
   - Run: `git checkout -b {branch-name}`

4. **Stage and commit changes:**
   - Stage all changes: `git add -A`
   - Write a clear, concise commit message that:
     - Starts with a verb in imperative mood (Add, Fix, Update, Refactor, etc.)
     - Summarizes the main change in one line
     - Uses bullet points for multiple changes
   - Commit with: `git commit -m "..."`
   - DO NOT include Claude co-author attribution

5. **Push to remote:**
   - Push branch: `git push -u origin {branch-name}`

6. **Create Pull Request:**
   - Write a SHORT and CONCISE PR:
     - **Title:** One line summary (max 72 chars)
     - **Body:**
       - Brief summary (2-3 sentences max)
       - Bullet points for key changes (3-5 bullets max)
       - If issue exists, add "Closes #{issue-number}" or "Fixes #{issue-number}" at the end
   - Use `gh pr create --base develop --title "..." --body "..."`
   - The "Closes #X" or "Fixes #X" keyword will automatically link and close the issue when merged

7. **Confirm:**
   - Share the PR URL with the user
   - Confirm the branch and issue linkage (if applicable)

## Important Notes
- Always use `--base develop` when creating PRs (not main)
- Keep PR title and body concise - avoid lengthy descriptions
- Use GitHub closing keywords (Closes, Fixes, Resolves) to auto-link issues
- DO NOT add Claude attribution or co-author tags
- If user mentions an issue number (e.g., "for issue #23"), use it in the branch name and PR body