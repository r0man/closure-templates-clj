(ns closure.templates.test.core
  (:import java.io.File java.net.URI com.google.template.soy.tofu.SoyTofu)
  (:use clojure.test
        closure.templates.classpath
        closure.templates.core
        closure.templates.test.compile
        closure.templates.test.render
        closure.templates.tofu))

(deftemplate hello-name [name]
  {:name name})

(deftest test-hello-name
  (is (= "Hello Closure!" (hello-name "Closure"))))

;; TODO: Infinite recursion when running via leiningen, but not in
;; repl. Namespace macro problem?

;; (deftest test-recompile!
;;   (is (tofu? (recompile!))))

(deftest test-compile-template
  (is-compiling compile-template))

(deftest test-render-template
  (is-rendering render-template))
