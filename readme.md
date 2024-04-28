Database:       H2 inmemory memcache [DB will be newly created every time service restarts.]
JDK:            17
spring-boot:    3.2.5
kafka:          3.6.2 from confluentinc's broker and zookeeper
security-type:  basic-auth for hitting REST endpoints
user-role:      [ADMIN, CLIENT] 
default-cred:   [
                    {username: admin, password: admin123, role: ADMIN}, 
                    {username: client, password: client123, role: CLIENT}   
                ]



prerequisits: 
    docker
    postman
    
steps:
    1: `docker compose up -d`
        pull 4 images (broker, zookeeper, user-service and journal-service) frorm docker-hub
    expected-ouput:
        ✔ Network assignments_default      Creat...                               0.1s 
        ✔ Container zookeeper              Started                                0.1s 
        ✔ Container broker                 Started                                0.1s 
        ✔ Container journal-svc-container  Started                                0.1s 
        ✔ Container user-svc-container     Started                                0.1s
    
    2: import the collectin in postman once, you will find following endpoits (considering all services will be running on localhost, don't update hostname)
        create Client/Admin user 
        update Client/admin user (id is mandatory)
        delete anyUser by ID
        fetchById
        view Journal of all above operations using journal/filter/list
    
    3. role based access
        all /admin/** endpots are only accessable to user having role ADMIN
        other endpots can be access by any role user given that credentials are correct
        password can be updated in postmane with newly added users from endots 
        
        