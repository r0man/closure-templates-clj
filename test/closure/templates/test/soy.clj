(ns closure.templates.test.soy
  (:import java.io.File java.net.URL)
  (:refer-clojure :exclude (replace))
  (:use [clojure.string :only (blank? replace)]
        clojure.test
        closure.templates.soy
        closure.templates.test))

(deftest test-soy
  (is (thrown-with-msg? IllegalArgumentException #"Not a Soy: not-existing"
        (soy "not-existing")))
  (is (thrown-with-msg? IllegalArgumentException #"Not a Soy: resources/soy"
        (soy "resources/soy")))
  (let [file (soy example-path)]
    (is (isa? (class file) URL))
    (is (= (.getAbsolutePath (File. example-path)) (.getPath file)))
    (is (= file (soy file)))))

(deftest test-soy?
  (is (not (soy? "not-existing")))
  (is (soy? example-path))
  (is (soy? (File. example-path))))

(deftest test-soy-seq
  (is (empty? (soy-seq "not-existing")))
  (let [soys (soy-seq "resources/soy")]
    (is (seq? soys))
    (is (= 1 (count soys)))
    (is (every? #(isa? (class %) URL) soys))))

(deftest test-soy-js
  (is (thrown-with-msg? java.lang.AssertionError #"" (soy-js nil)))
  (is (thrown-with-msg? java.lang.AssertionError #"" (soy-js "")))
  (is (= (str *ns* ".helloWorld") (soy-js 'hello-world)))
  (is (= "user.helloWorld" (soy-js 'hello-world "user"))))

(deftest test-soy-path
  (is (thrown-with-msg? java.lang.AssertionError #"" (soy-path nil)))
  (is (thrown-with-msg? java.lang.AssertionError #"" (soy-path "")))
  (is (= (str "soy/" (replace (str *ns*) "." File/separator) ".soy") (soy-path "hello-world")))
  (is (= "soy/user.soy" (soy-path "hello-world" "user"))))
