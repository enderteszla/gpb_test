javac -d target/ src/gpb_test/task2/* src/gpb_test/common/*
jar cvfm task2.jar Task2.MF -C target gpb_test/task2/ -C target gpb_test/common/
