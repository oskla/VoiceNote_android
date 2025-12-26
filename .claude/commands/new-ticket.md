# Create New Ticket

You are helping the user create a new GitHub issue and add it to their VoiceNote project.

## Important Notes

- Keep issue descriptions clear and actionable
- Use markdown formatting for better readability
- Include relevant labels if appropriate (enhancement, bug, etc.)
- The issue will default to "Backlog" status when added to the project

## Instructions

1. **Gather information from the user:**
    - The user will provide a description of what they want implemented
    - Ask clarifying questions if needed to create a well-structured issue

2. **Create the GitHub issue:**
    - Write a clear, well-structured issue with:
        - **Title:** Concise, descriptive title
        - **Body:**
            - Description of the feature/fix/enhancement
            - Current vs expected behavior (if applicable)
            - Benefits or rationale
            - Any implementation notes or suggestions
    - Use `gh issue create --title "..." --body "..."`

3. **Add issue to VoiceNote project:**
    - After creating the issue, add it to the VoiceNote project (project #2)
    - Run:
      `gh project item-add 2 --owner oskla --url https://github.com/oskla/VoiceNote_android/issues/{issue-number}`
    - Make sure it has the "Backlog" status

4. **Confirm:**
    - Share the issue URL with the user
    - Confirm it has been added to the project