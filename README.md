# Per Scholas - Case Study
**Deliver a Java web application created using Spring MVC or Spring Boot**  
Below is the description of the project.

# What Goes Where in Redmond
- To search how to dispose of anything in Redmond, WA, USA
- Users can also participate in growing the knowledge by adding more items!

## Technical Specifications
A Java web application built using Spring MVC framework
- Java 11
- Web server: Tomcat v9.0
- Framework: Spring MVC v5.3.8
- Session management: Spring Security v5.5.1
- Database: MariaDB v2.6.2
- Eclipse Link (JPA) v2.7.6
- Javax Validation v2.0.1 Final
- JSTL v1.2
- Mockito v3.2.4
- JUnit v1.6.2
- Jupiter v5.7.2

## Model (@Entity)
- Credential: username, password
- User: email, first name, last name, items, joined date
- Item: name, condition, best option, special instruction, notes, added date
- UserItem (Join Table): User to Item is mapped with @OneToMany relationship

## View (@Service)
Implemented via @Autowired repositories (@Repository)
- CredentialService
- UserService
- ItemService
- UserItemService (Join Table)

## Controller (@Controller)
- HomeController
    - Handles exceptions caused by services.

## JPA
- persistence.xml includes model classes, JDBC connection information, and Eclipse Link configurations
- Custom queries: 
    - CredentialRepsotiroy
       - findByUsername
       - findByUsernameAndPassword
    - ItemRepository
       - findByName
       - findByNameAndState
    - UserRepository
       - findByEmail
    - UserItemRepository
       - findByUserId
       - findByItemId
       - deleteByUserId
       - deleteByItemId

## Custom Exceptions
- CredentialAlreadyExistsException: Credential's add() method
- CredentialNotFoundException: findByNameAndPassword() and update() methods
- ItemAlreadyExistsException: add() and update() methods

## JUnit Testing
Tests all methods in each Service class.
- Credential
    - Integrated Test for CredentialService class
       - CredentialAlreadyExistsException is tested with add() method
       - CredentialNotFoundException is tested with findByUsernameAndPassword() method
- User
    - Integrated Test for UserService class
    - Mockito Test for UserService class
- UserItem
    - Integrated Test for UserItemService class
- Item 
    - Parameterized Test for Item class
    - Mockito Test for ItemService class
    - Test Suite for the two above
    - Integrated Test for ItemService class
       - ItemAlreadyExistsException is tested for add() method 

## Views (Front-end)
- JSP (12 pages): index, find, about, list, login, register, profile, delete_user, add_item, edit_item, contact, and admin
- CSS
    - custom external file: stylesheet.css
    - Bootstrap
    - internal styling
- Javascript
    - add_table_head: adds table head when showing search results in the main page
    - go_back: links the previous page to a button
    - internal scripts to change headers and footers depending on roles
- Navigation
    - header and footer are included in every JSP
    
## Spring Security
- Credential class holds username, password, and user's role.
- CurrentCredential class holds Credential in the current session.
- Anonymous user can access index, find, about, list, login, register, and contact page.
- ROLE_USER can additionally access profile, add_item, edit_item, and delete_user page.
- ROLE_ADMIN can additionally access admin page.

## Utility classes
- BestOption (enum): 4 different disposal options for Users to choose from
- [ ] Queries
- [ ] Named queries
- [ ] HTML pages
- [ ] URL patterns


## Functional Specifications (What does it do?)
- To search how to dispose of anything in Redmond, WA, USA
- Users can also participate in growing the knowledge by adding more items!

## User Stories
- As a user, I want to search for an item so that I can get information on how to dispose of it. (R)
- As a user, I want to see a list of all items this website has so that I can look into similar items that I’m trying to search. (R)
- As a user, I want to create an account on this website so that I can keep track of my contribution to the website. (C)
- As a user, I want the register form to be populated when my username already exists and the page is refreshed.
- As a user, I want to log in to the website so that I can check my account information.
- As a user, I want to see a list of items that I’ve added to the website on my profile page so that I can keep track and manipulate the items easily. (R)
- As a user, I want to sign out so that my credentials are securely removed from the browser.
- As a user, I want to add a new item to the website’s list so that I can search for the same item later. (C)
- As a user, I want to have options to choose from for "best option" category when adding/editing an item.
- As a user, I want to be warned when adding a new item so that I don't add a duplicate item.
- As a user, I want to modify items that I have added to the website so that I can update incorrect information. (U)
- As a user, I want to go back from update item page to the profile page so that I can change my mind if I want to.
- As a user, I want to delete items that I have added to the website so that I can remove unrelated information. (D)
- As a user, I want to delete my account so that I stop using the service. (D)
- [ ] As a user, I want to change my password (U)
- [ ] As a user, I want to see the entire list sorted in alphabetical order with navigation for each letter so that I can look for items faster and I don’t have to scroll up and down the web page too much. (Bootstrap)
- [ ] As a user, I want to contact the support team so that I can share my opinions and make suggestions to the website. (Email functionality)
- [ ] As a user, I want to expect the same user interface experience regardless of which devices I’m using. (CSS)
- [ ] As an admin, I want to see all the users. (R)