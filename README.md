# Welcome to my fancy AnyNote app

Take notes, update and delete them.

## Known users:
* `ted`, password `teddy`
* `fred`, password `freddy`

## Vulnerabilities

### A3 - XSS
Try some fancy HTML in the title box!

#### Solution
Use `th:text` instead of `th:utext`

### A4 - Insecure direct object references
Try to see someone else's notes by changing the id in the url to another number... 1 for `ted`, 2 for `fred`. You can also delete and 
edit notes of someone else.

#### Solution
Don't allow specifying the user-id in the URL, but get it from the Account. Also, verify whether you delete one of your own notes instead of someone elses.

### A5 - Security misconfiguration
When you want to change password, the old one is filled in for you :O (so we must store it plain-text)

#### Solution
Use a better password encoder (bcrypt or alike)

### A6 - Sensitive Data Exposure
When you want to change password, the old one is filled in for you :O. Check the page source...

#### Solution
Don't send the original password to the user

### A8 - CSRF
You can hijack someones session by stealing the session id..

#### Solution
Enable CSRF security




