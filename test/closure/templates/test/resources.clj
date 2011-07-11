(ns closure.templates.test.resources
  (:import [com.google.template.soy SoyFileSet]
           [com.google.template.soy.tofu SoyTofu]
           java.io.File)
  (:use clojure.test
        closure.templates.classpath
        closure.templates.resources))

(def example-soy-path "resources/soy/closure/templates/test/core.soy")

(deftest test-add-soy-file!
  (let [file (soy-file example-soy-path)]
    (clear-fileset!)
    (add-soy-file! file)
    (is (= 1 (count @*fileset*)))
    (is (contains? @*fileset* file))))

(deftest test-clear-fileset!
  (add-soy-file! (soy-file example-soy-path))
  (clear-fileset!)
  (is (empty? @*fileset*)))

(deftest test-compile-fileset
  (let [tofu (compile-fileset (soy-file-seq "resources/soy"))]
    (is (isa? (class tofu) SoyTofu))))

(deftest test-soy-file
  (is (thrown-with-msg? IllegalArgumentException #"Not a Soy file: not-existing"
        (soy-file "not-existing")))
  (is (thrown-with-msg? IllegalArgumentException #"Not a Soy file: resources/soy"
        (soy-file "resources/soy")))
  (let [file (soy-file example-soy-path)]
    (is (isa? (class file) File))
    (is (= example-soy-path (.getPath file)))))

(deftest test-soy-file?
  (is (not (soy-file? "not-existing")))
  (is (soy-file? example-soy-path))
  (is (soy-file? (File. example-soy-path))))

(deftest test-soy-file-seq
  (is (empty? (soy-file-seq "not-existing")))
  (let [soys (soy-file-seq "resources/soy")]
    (is (seq? soys))
    (is (= 1 (count soys)))
    (is (every? #(isa? (class %) File) soys))))

(deftest test-render-template
  (let [tofu (compile-fileset (soy-file-seq "resources/soy"))]
    (is (= "Hello closure!" (render-template tofu "closure.templates.test.core.helloName" {"name" "closure"})))))

(deftest test-template-name
  (is (thrown-with-msg? java.lang.AssertionError #"" (template-name nil)))
  (is (thrown-with-msg? java.lang.AssertionError #"" (template-name "")))
  (are [name expected]
    (is (= expected (template-name name)))
    "hello-world" "helloWorld"
    "example/hello-world" "example.helloWorld"
    "my-example/hello-world" "myExample.helloWorld"))

(deftest test-template-path
  (is (thrown-with-msg? java.lang.AssertionError #"" (template-path nil)))
  (is (thrown-with-msg? java.lang.AssertionError #"" (template-path "")))
  (are [name expected]
    (is (= expected (template-path name)))
    "user/hello-world" "soy/user.soy"
    "my-example/hello-world" "soy/my_example.soy"
    "closure.templates.test.core/hello-name" "soy/closure/templates/test/core.soy"))

(deftest test-underscore-keys
  (are [m expected]
    (is (= expected (underscore-keys m)))
    {:a-1 {:b-2 {:c-3 1}}} {"a_1" {"b_2" {"c_3" 1}}}))

;; (deftest test-deftemplate
;;   (deftemplate test-hello-name "example.helloName"
;;     [data] data)
;;   (with-fileset [(classpath-file "soy")]
;;     (is (= "Hello Test!" (test-hello-name {:name "Test"})))))
