(ns closure.templates.test.render
  (:import java.io.File java.net.URI com.google.template.soy.tofu.SoyTofu)
  (:use clojure.test
        closure.templates.core
        closure.templates.render
        closure.templates.test))

(defn is-rendering [render-fn]
  (let [template "closure.templates.test.core.helloName"
        check #(is (= "Hello Closure!" (render-fn % template {:name "Closure"} nil)))]
    (testing "with nil"
      (is (nil? (render nil nil nil nil))))
    (testing "with file"
      (check example-file))
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
      (check (compile-template example-file)))
    (testing "with uri"
      (check example-uri))
    (testing "with url"
      (check example-url))))

(deftest test-render
  (is-rendering render))

(deftest test-underscore-keys
  (are [m expected]
    (is (= expected (underscore-keys m)))
    {:a-1 {:b-2 {:c-3 1}}} {"a_1" {"b_2" {"c_3" 1}}}))
