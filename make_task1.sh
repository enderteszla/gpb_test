javac -d target/ src/gpb_test/task1/* src/gpb_test/common/*
jar cvfm task1.jar Task1.MF -C target gpb_test/task1/ -C target gpb_test/common/
