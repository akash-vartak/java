all:
	mvn install
clean:
	mvn clean
	find . -name target -type d -exec rm -rf "{}" \;
deploy:
	make deploydevel
package:
	make packagedevel
deploydevel:
	cd bridge/war/devel && make deploy
packagedevel:
	cd bridge/war/devel && make installwotest
builddevel:
	mak packagedevel
deployoffice:
	cd bridge/war/office && make deploy
packageoffice:
	cd bridge/war/office && make installwotest
buildoffice:
	make packageoffice
installwotest:
	mvn -Dmaven.test.skip=true install
update:
	make all deploy
test:
	mvn test
osgitest:
	find . -type d -d 1 -name "*_test" -exec sh -c 'cd {} && make' \;
guitest:
	find . -type d -d 1 -name "*_gui" -exec sh -c 'cd {} && make' \;
apitest:
	find . -type d -d 1 -name "*_api" -exec sh -c 'cd {} && make' \;
console:
	telnet localhost 5555
cleanup:
	find . -name '.DS_Store' -exec rm -rf "{}" \;
	find . -name '.svn' -exec rm -rf "{}" \;
	find . -name 'target' -exec rm -rf "{}" \;
buildwebsite:
	cd website && make all
buildlunch:
	cd lunch_api && make all
	cd lunch && make all
	cd lunch_gui && make all
	cd lunch_test && make all
buildportfolio:
	cd portfolio_api && make all
	cd portfolio && make all
	cd portfolio_gui && make all
	cd portfolio_test && make all
buildconfiguration:
	cd configuration_api && make all
	cd configuration && make all
	cd configuration_tools && make all
	cd configuration_gui && make all
	cd configuration_test && make all
buildstorage:
	cd storage_api && make all
	cd storage && make all
	cd storage_tools && make all
	cd storage_gui && make all
	cd storage_test && make all
buildloggly:
	cd loggly_api && make all
	cd loggly && make all
	cd loggly_gui && make all
	cd loggly_test && make all
buildworktime:
	cd worktime_api && make all
	cd worktime && make all
	cd worktime_gui && make all
	cd worktime_test && make all
buildgallery:
	cd gallery_api && make all
	cd gallery && make all
	cd gallery_gui && make all
	cd gallery_test && make all
buildweather:
	cd weather_api && make all
	cd weather && make all
	cd weather_gui && make all
	cd weather_test && make all
buildgwt:
	cd gwt_api && make all
	cd gwt && make all
	cd gwt_gui && make all
	cd gwt_test && make all
buildblog:
	cd blog_api && make all
	cd blog && make all
	cd blog_gui && make all
	cd blog_test && make all
buildwiki:
	cd wiki_api && make all
	cd wiki && make all
	cd wiki_gui && make all
	cd wiki_test && make all
buildslash:
	cd slash_api && make all
	cd slash && make all
	cd slash_gui && make all
	cd slash_test && make all
buildbookmark:
	cd bookmark_api && make all
	cd bookmark && make all
	cd bookmark_gui && make all
	cd bookmark_test && make all
buildmonitoring:
	cd monitoring_api && make all
	cd monitoring && make all
	cd monitoring_gui && make all
	cd monitoring_test && make all
buildauth:
	cd authentication && make all
	cd authentication_api && make all
	cd authentication_gui && make all
	cd authentication_test && make all
	cd authorization && make all
	cd authorization_api && make all
	cd authorization_gui && make all
	cd authorization_test && make all
dllir:
	find . -type d -d 1 -exec sh -c 'cd {} &&  make dir' \;
sonar:
	mvn sonar:sonar
help:
	echo "help"
findwrongnamedtests:
	find . -name "*Test.java" | grep -v UnitTest | grep -v IntegrationTest
telnet:
	telnet localhost 5555
uploadoffice:
	scp bridge/war/office/target/bridge_office.war bborbe@bborbe.devel.lf.seibert-media.net:~/
