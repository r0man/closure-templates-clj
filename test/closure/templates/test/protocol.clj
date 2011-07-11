(ns closure.templates.test.protocol
  (:refer-clojure :exclude (compile))
  (:import java.io.File java.net.URI com.google.template.soy.tofu.SoyTofu)
  (:use clojure.test
        closure.templates.classpath
        closure.templates.fileset
        closure.templates.protocol
        closure.templates.test
        closure.templates.tofu))

(defn is-compiling [compile-fn]
  (let [check #(is (tofu? (compile-fn %)))]
    (testing "with nil"
      (is (nil? (compile nil))))
    (testing "with seq of files"
      (check (seq [example-file])))
    (testing "with seq of filenames"
      (check (seq [example-path])))
    (testing "with seq of duplicates"
      (check (seq [example-path example-file])))
    (testing "with vector"
      (check (seq [example-file])))
    (testing "with set"
      (check (set [(File. example-uri)])))
    (testing "with string"
      (check example-path))
    (testing "with tofu"
      (check (compile example-path)))
    (testing "with uri"
      (check example-uri))
    (testing "with url"
      (check example-url))))

(defn is-rendering [render-fn]
  (let [template "closure.templates.test.core.helloName"
        check #(is (= "Hello Closure!" (render-fn % template {:name "Closure"} nil)))]
    (testing "with nil"
      (is (nil? (render nil nil nil nil))))
    (testing "with file"
      (check example-file))
    (testing "with tofu"
      (check (compile example-file)))
    (testing "with seq of files"
      (check (seq [example-file])))
    (testing "with seq of filenames"
      (check (seq [example-path])))
    (testing "with seq of duplicates"
      (check (seq [example-path example-file])))
    (testing "with vector"
      (check [example-file]))
    (testing "with set"
      (check (set [example-file])))
    (testing "with string"
      (check example-path))
    (testing "with tofu"
      (check (compile example-path)))
    (testing "with uri"
      (check example-uri))
    (testing "with url"
      (check example-url))))

(deftest test-compile
  (is-compiling compile))

(deftest test-render
  (is-rendering render))

(deftest test-underscore-keys
  (are [m expected]
    (is (= expected (underscore-keys m)))
    {:a-1 {:b-2 {:c-3 1}}} {"a_1" {"b_2" {"c_3" 1}}}))