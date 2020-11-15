# DC3160 Coursework Dan Riggs README
The deployed project can be found at the URL: 
The GitHub project can be found at URL: https://github.com/RiggsDan/DC3160

## Sections attempted
All sections of the coursework have been fully completed, testing evidence for this can be found under https://github.com/RiggsDan/DC3160/tree/master/Paperwork

## Implementation decisions made
* Use of PreparedStatements for all database interactions
  * PreparedStatements wrap the parameters passed in, ensuring they are not evaluated for execution, this stops SQL injection from being a vulnerablility of the system

* The introduction of a 'DatabaseConnection' object that implements 'AutoClosable'
 * A common problem with applications that interact with external resources, such as database connections, is that of resource leaks. This occurs when
   a resource is not released properly. Using the 'try-with-resources' functionality of java ensures the resource is released properly.

* The scope of the Lessons collection object for the LessonsTimetableView
 * The trade offs to consider when deciding the scope of the Lessons collection are as follows: 'Database query number', 'Memory utilisation', 'refresh time'
 * The following requirements were thus considered for the scope:
  * Upon login, the user should be displayed with the most up to date view of the database - Neccessitates a session scope or lower
  * The user should not have to sign out and back in to have the view refreshed - Necessitates either the list to be refetched every time that page is loaded (Lots of database queries) OR a refresh mechanism to be in-place
 * Based on the above considerations a 'Session' scope with a refresh mechanism was selected as it minimises database queries whilst ensuring 
   the page is never more than 60 seconds out of sync for the user (And is guaranteed up to date on sign-in)

* Class renaming from the provided code
 * To align the functions of the classes with their names according to the standard naming convention (Such as database interaction objects being DAO's) many classes were renamed

* Implementation of logging
 * To aid debugging as well as provide confidence on how the application functioned, a 'SystemLogger' was included 

* Defensive implementation of session management
 * In order to avoid malicious usage of the program by attempting to use functionality without a session, the functions have been split into two categories 'Those requiring a session' and 'Those that do not require a session'
   this restriction enables the system to stop users accessing content they shouldn't be able to without a login (Code found in Controller.java and ControllerAction.java)

## Evidence of high standards of deliverable
* A testing plan was created with all required scenarios for the specification was created and executed. (Found here: https://github.com/RiggsDan/DC3160/tree/master/Paperwork)
* An extensive junit testing suite was created which has given an overall coverage of 84.0%, with the cases not covered being those exceptions which will occur mainly with network instability
* The code is well commented and follows well established patterns of design and naming conventions
* The code has been analysed using SonarLint (Based on the SonarQube code quality analysis tool), there are no 'Critical' or 'Blocker' issues and of the few issues that are flagged below that are non-functional impact