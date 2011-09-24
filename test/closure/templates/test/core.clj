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

(deftest test-compile-template
  (is-compiling compile-template))

(deftest test-render-template
  (is-rendering render-template))

(deftest test-hello-name
  (is (= "Hello Closure!" (hello-name "Closure")))
  (is (= "Hello 1!" (hello-name 1))))
