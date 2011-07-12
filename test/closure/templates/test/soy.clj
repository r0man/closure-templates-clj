(ns closure.templates.test.soy
  (:import java.io.File)
  (:refer-clojure :exclude (replace))
  (:use [clojure.string :only (blank? replace)]
        clojure.test
        closure.templates.soy
        closure.templates.test))

(deftest test-soy-file
  (is (thrown-with-msg? IllegalArgumentException #"Not a Soy file: not-existing"
        (soy-file "not-existing")))
  (is (thrown-with-msg? IllegalArgumentException #"Not a Soy file: resources/soy"
        (soy-file "resources/soy")))
  (let [file (soy-file example-path)]
    (is (isa? (class file) File))
    (is (= example-path (.getPath file)))))

(deftest test-soy-file?
  (is (not (soy-file? "not-existing")))
  (is (soy-file? example-path))
  (is (soy-file? (File. example-path))))

(deftest test-soy-file-seq
  (is (empty? (soy-file-seq "not-existing")))
  (let [soys (soy-file-seq "resources/soy")]
    (is (seq? soys))
    (is (= 1 (count soys)))
    (is (every? #(isa? (class %) File) soys))))

(deftest test-template-name
  (is (thrown-with-msg? java.lang.AssertionError #"" (template-name nil)))
  (is (thrown-with-msg? java.lang.AssertionError #"" (template-name "")))
  (is (= (str *ns* ".helloWorld") (template-name 'hello-world)))
  (is (= "user.helloWorld" (template-name 'hello-world "user"))))

(replace (str *ns*) "." File/separator)

(deftest test-template-path
  (is (thrown-with-msg? java.lang.AssertionError #"" (template-path nil)))
  (is (thrown-with-msg? java.lang.AssertionError #"" (template-path "")))
  (is (= (str "soy/" (replace (str *ns*) "." File/separator) ".soy") (template-path "hello-world")))
  (is (= "soy/user.soy" (template-path "hello-world" "user"))))
