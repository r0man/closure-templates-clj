(ns closure.templates.test.protocol
  (:refer-clojure :exclude (compile))
  (:import java.io.File java.net.URI com.google.template.soy.tofu.SoyTofu)
  (:use clojure.test
        closure.templates.core
        closure.templates.classpath
        closure.templates.protocol))

(def example-soy "soy/closure/templates/test/core.soy")
(def example-path (str "resources/" example-soy))
(def example-file (File. example-path))
(def example-uri (classpath-uri example-soy))
(def example-url (classpath-url example-soy))

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
