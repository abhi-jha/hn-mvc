mvn spring-boot:run > ~/hn-mvc.log 2>&1
./run_instances.sh &>/dev/null & disown;
./run_instances.sh > ~/hn-mvc.log 2>&1
