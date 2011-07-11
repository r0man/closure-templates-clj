(ns closure.templates.test.compile
  (:refer-clojure :exclude (compile))
  (:use clojure.test
        closure.templates.compile
        closure.templates.test
        closure.templates.tofu))

(defn is-compiling [compile-fn]
  (let [check #(is (tofu? (compile-fn %)))]
    (testing "with nil"
      (is (nil? (compile nil))))
    ;; (testing "with seq of files"
    ;;   (check (seq [example-file])))
    ;; (testing "with seq of filenames"
    ;;   (check (seq [example-path])))
    ;; (testing "with seq of duplicates"
    ;;   (check (seq [example-path example-file])))
    ;; (testing "with vector"
    ;;   (check (seq [example-file])))
    ;; (testing "with set"
    ;;   (check (set [example-file])))
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
