Week 3:
Level-3 - used claude to make my change of the arraylist to ArrayList<String[]> consistent across all parameters
Level-4 - I tried to see if claude could generate the entire set of 3 commands on its own, it was pretty accurate but I had to make certain changes irregardless, to make the code simpler, but largely driven by claude.
Level-6 - used claude to verify any edge cases, it added more error catching messages.
UI - used antigravity to come up with the testcases. Took a while for it to get context, but once it did, it was pretty good.

Week 4:
Level-7 - used antigravity to create changes, it messed up a bit but eventually got it to work. Probably easier to manually code it though.
Level-8 - similar experience as Level-7, but I still continued with antigravity as I am trying to do the entirety of this week's tasks with its help. It ended up creating separate files for each task, which was not what I wanted, but I eventually got it to work.
A-MoreOOP & A-Packages - after I wrote a bit of information giving antigravity more context into how I wanted things to look like, and the structure I wanted, it was able to generate the code pretty well, with minimal errors.
A-Gradle, A-JUnit, A-JavaDoc, A-CodingStandard - antigravity successfully implemented Gradle support, JUnit tests (100% pass), comprehensive JavaDocumentation, and aligned the entire codebase with SE-EDU coding standards.
For this week I more or less tried doing everything via antigravity. I didnt really have any issues, but I will try to do it less in the future as at some point in the week's tasks I lost track of what was happening to the code, it seemed more autopilot and hence took me much longer to fix things.


Week 5:
I used claude code to implement most of the tasks, and it was hands-down one of the best ai coding experiences I have had. It was able to handle most things on its own. The only issue I faced was that it by default created a branch and pushed all branches to that 1 branch, which is why I had to merge everything back and recreate some stuff. But overall amazing coding assistant

Week 6:
This week I switched back to antigravity as I ran out of claude code free credits. This was 100x worse than claude. It got super confused and messed up my whole repo. I had to reverse a lot of changes and reimplement them

Week 7:
A-BetterGui - used Claude to redesign the DialogBox component: removing large placeholder images, adding asymmetric chat layout, and introducing the MercuryResponse class to enable error-specific styling. Claude scaffolded the CSS error-dialog class as well.
A-Personality - used Claude to rewrite Ui.java response messages with Mercury's messenger-god personality, including mission metaphors for task operations and a revised welcome/goodbye sequence.
A-MoreErrorHandling - used Claude to comprehensively rewrite Parser.java: added input normalization, more precise error messages naming the invalid token, validation of empty fields, and event time ordering. Also added duplicate detection in AddCommand.
A-MoreTesting - used Claude to generate ~80 new JUnit tests across StorageTest, MarkUnmarkCommandTest, DeleteCommandTest, AddCommandTest, ParserMoreTest, TaskListMoreTest, DeadlineMoreTest, and EventMoreTest.
A-Week7-Mysterious (StatsCommand) - used Claude to design and implement the stats command: StatsCommand.java, a showStats() method in Ui.java, and integration into the parser.
Overall Week 7: Claude was used heavily for boilerplate generation and comprehensive test coverage. Manual review was needed to ensure tests matched the actual master-branch behavior (e.g., no whitespace normalization on master). The AI-assisted approach saved ~2-3 hours of writing repetitive test cases. Key lesson: always verify AI-generated tests against the actual production code behavior before committing.
