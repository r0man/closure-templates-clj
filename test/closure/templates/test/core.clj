(ns closure.templates.test.core
  (:import java.io.File java.net.URI com.google.template.soy.tofu.SoyTofu)
  (:use clojure.test
        closure.templates.test.protocol
        closure.templates.classpath
        closure.templates.core))

(deftemplate hello-name [name]
  {:name name})

;; (deftest test-hello-name
;;   (is (= "Hello Closure!" (hello-name "Closure"))))

;; (deftest test-recompile!
;;   (is (tofu? (recompile!))))

(deftest test-compile-template
  (is-compiling compile-template))

(deftest test-render-template
  (is-rendering render-template))

(deftest test-tofu?
  (is (not (tofu? nil)))
  (is (not (tofu? "")))
  (is (tofu? (compile-template example-file))))
