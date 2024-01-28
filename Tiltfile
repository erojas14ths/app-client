# OS Identification
mvn = "./mvnw"
expected_ref = "$EXPECTED_REF"
if os.name == "nt":
  mvn = "mvn"
  expected_ref = "%EXPECTED_REF%"

# Commands
commandMvn = mvn + ' clean package ';
commandDocker = 'docker build -t '+ expected_ref + ' .';

# Build
custom_build(
    # Name of the container image
    ref = 'app-client',
    # Command to build the container image
    command = commandMvn + ' && ' + commandDocker,
    # Files to watch that trigger a new build
    deps = ['pom.xml', 'src']
)

# Deploy
k8s_yaml(['k8s/deployment.yml', 'k8s/service.yml'])

# Manage
k8s_resource('app-client', port_forwards=['8080'])