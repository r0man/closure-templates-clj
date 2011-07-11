(ns closure.templates.test.classpath
  (:import [java.net URI URL] java.io.File)
  (:use closure.templates.resources
        closure.templates.classpath
        clojure.test))

(deftest test-classpath-file
  (is (thrown? java.io.FileNotFoundException (classpath-file "not-existing")))
  (let [directory (classpath-file *directory*)]
    (is (isa? (class directory) java.io.File))
    (is (.isDirectory directory)))
  (let [file (classpath-file (str *directory* "/closure/templates/test/core.soy"))]
    (is (isa? (class file) java.io.File))
    (is (.isFile file))))

(deftest test-classpath-url
  (is (thrown? java.io.FileNotFoundException (classpath-url "not-existing")))
  (let [url (classpath-url *directory*)]
    (is (isa? (class url) java.net.URL))))

(deftest test-classpath-uri
  (is (thrown? java.io.FileNotFoundException (classpath-uri "not-existing")))
  (let [uri (classpath-uri *directory*)]
    (is (isa? (class uri) java.net.URI))))
