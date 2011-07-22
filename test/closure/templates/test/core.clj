(ns closure.templates.test.core
  (:refer-clojure :exclude (compile))
  (:import java.io.File java.net.URI com.google.template.soy.tofu.SoyTofu)
  (:use [closure.templates.test.compile :only (is-compiling)]
        [closure.templates.test.render :only (is-rendering)]
        clojure.test
        closure.templates.core
        closure.templates.tofu))

(deftemplate hello-name [name]
  {:name name})

(deftest test-compile
  (is-compiling compile))

(deftest test-compile!
  (is (tofu? (compile!))))

(deftest test-render
  (is-rendering render))

(deftest test-hello-name
  (is (= "Hello Closure!" (hello-name "Closure"))))
