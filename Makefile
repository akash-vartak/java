installwotest:
	mvn -Dmaven.test.skip=true install
test:
	mvn test
cleanup:
	find . -name '.DS_Store' -exec rm -rf "{}" \;
	find . -name '.svn' -exec rm -rf "{}" \;
	find . -name 'target' -exec rm -rf "{}" \;
deploy:
	cd bridge && make deploy
