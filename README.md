# Docker_Selenium
This project has all concepts nicely used like Selnium TestNG, Listeners along with screenshots for failed cases, Extent Reports,
Dockers, Headless execution, Selenium Grid through Standard Selenium docker images for Hub and Node.
Run Docker container
	Docker run nginx
# List Containers
	Docker ps //list all running containers
	Docker ps -a //lists all containers irrespective of their state
	
# Stop a running container
	Docker stop <<container id>> or <<name>>
# Remove a stopped/Exited container permanently
	Docker rm <<container id>> or <<name>>
# Remove all the non running containers
	Docker system prune
# List images 
	Docker images

# Remove images permanently 
	Docker rmi <image id or name>>
	
# only pull the image
	Docker pull <<image name>>
	
# execute a command in container
	Docker exec centos cat /etc/hosts //this will run the cat command inside centos container and exit
# Run in detached mode
	Docker run -d <<name of the container>> //this will keep running the container in the background so that we can continue in current command prompt
# Run in attach mode
	Docker run -d timer //this run in detached mode in background
	Docker attach $(docker ps) or docker attach <<container id>> will attach it to docker terminal
	Ex: docker run -d  timer
	Docker ps
	Docker attach 8902lk34234l
# Run the docker container and do not exit
	Docker exec -it centos bash    //this is opening centos container and running bash command on it so it will stay 
	C:\Users\rgajul>docker run -it centos bash
	[root@f6bef4abd8fb /]# cat etc/*release*
	CentOS Linux release 8.3.2011
	Derived from Red Hat Enterprise Linux 8.3
	NAME="CentOS Linux"
	VERSION="8"
	ID="centos"
	ID_LIKE="rhel fedora"
	VERSION_ID="8"
	PLATFORM_ID="platform:el8"
	PRETTY_NAME="CentOS Linux 8"
	ANSI_COLOR="0;31"
	CPE_NAME="cpe:/o:centos:centos:8"
	HOME_URL="https://centos.org/"
	BUG_REPORT_URL="https://bugs.centos.org/"
	CENTOS_MANTISBT_PROJECT="CentOS-8"
	CENTOS_MANTISBT_PROJECT_VERSION="8"
	CentOS Linux release 8.3.2011
	CentOS Linux release 8.3.2011
	cpe:/o:centos:centos:8
# find the latest version of docker/server
	Docker version
# tag 
	Docker run centos:5.0.5 //if no tag is provided docker pull the latest version by using :latest as the tag
#Run the docker in interactive mode and attach the dockers terminal
	Docker exec -it centos bash
# Port-Mapping
	Syntax :Docker run -p <hostport>:<dockerport> <image> 
	Example: docker run -p 8080:5000 kodecloud/simple-webapp
# Volume-Mapping
	Syntax :Docker run -v <hostdir>:<dockerdir> <containername> <cmd>
	Example :Docker run -v /opt/datadir:/var/lib/mysql mysql
# inspect container
	Syntax: Docker inspect <container name or id>
	Example: Docker inspect centos
# container logs
	Syntax : docker logs <<container id or name>>
	Example: docker logs centos
# Build an image using docker file
	Docker build . -f Dockerfile
# Give your image a specific tag
	Docker build . -f Dockerfile -t RaviGajul/Jenkins
# Make the image available publicly
	Docker login
	Useraccount: ravigajul
	Password: *********
	Docker push RaviGajul/Jenkins
# Run an instance of 

kodekloud/simple-webapp with a tag blue and map port 8080 on the container to 38282 on the host.
docker run -p 38282:8080 kodekloud/simple-webapp:blue

# Build a docker image 
using the Dockerfile and name it webapp-color. No tag to be specified
Docker build . -t webapp-color

# Pass environment Variables to docker command
Docker run -e app_color=blue webapp-color

# inspect current environment variable being used
	Docker inspect <<name of container>> in the output of this command, check the config object for env variables.
	Docker exec -it <<nameofcontainer>> env
	
# Run a container named blue-app using image kodekloud/simple-webapp and set the environment variable APP_COLOR to blue. Make the application available on port 38282 on the host. The application listens on port 
docker run -e APP_COLOR=blue -p 38282:8080 --name blue-app kodekloud/simple-webapp

# Difference between CMD and Entry Point
Ubuntu-sleeper -container name
FROM Ubuntu
CMD ["Sleep","5"] 
By default the container sleeps for 5 seconds and exits to override the sleep time we can pass the new time in docker run like below

Docker run ubuntu-sleeper sleep 10  //this will make the container sleep for 10 seconds before it exits. Passing sleep10 again is not a good practise as 
unbuntu-sleeper is already created to sleep for certain seconds. So a good code is to just pass the 10 instead of sleep10 which can be achieved by entrypoint

Ubuntu-sleeper -container name
From Ubuntu
ENTRYPOINT [ "Sleep"]
Docker run ubuntu-sleeper 10 ….this will automatically append 10 to the sleep command in entry point.

# Docker-Compose
	Better way to use when its needed to set up multiple services
# --link 
	This is a cli argument to link two different containers
		Docker run  -d --name=vote -p 5000:80 --link redis:redis voting-app  //we are linking redis to voting-app image
			Here first redis is the name of the image
			Second redis is the name of the host that voting-app is configured to look for.

