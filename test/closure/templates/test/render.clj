(ns closure.templates.test.render
  (:refer-clojure :exclude (compile))
  (:import java.io.File java.net.URI com.google.template.soy.tofu.SoyTofu)
  (:use [closure.templates.compile :only (compile)]
        clojure.test
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
      (check (compile example-file)))
    (testing "with uri"
      (check example-uri))
    (testing "with url"
      (check example-url))))

(deftest test-render
  (is-rendering render))
