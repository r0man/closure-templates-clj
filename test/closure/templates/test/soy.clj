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

(deftest test-fn-name->js-name
  (is (thrown-with-msg? java.lang.AssertionError #"" (fn-name->js-name nil)))
  (is (thrown-with-msg? java.lang.AssertionError #"" (fn-name->js-name "")))
  (is (= (str *ns* ".helloWorld") (fn-name->js-name 'hello-world)))
  (is (= "user.helloWorld" (fn-name->js-name 'hello-world "user"))))

(deftest test-fn-name->soy-path
  (is (thrown-with-msg? java.lang.AssertionError #"" (fn-name->soy-path nil)))
  (is (thrown-with-msg? java.lang.AssertionError #"" (fn-name->soy-path "")))
  (is (= (str "soy/" (replace (str *ns*) "." File/separator) ".soy") (fn-name->soy-path "hello-world")))
  (is (= "soy/user.soy" (fn-name->soy-path "hello-world" "user"))))
