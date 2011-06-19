(ns closure.templates.test.soy
  (:import [java.net URI] java.io.File)
  (:use closure.templates.soy
        clojure.test))

(deftest test-soy-file?
  (is (not (soy-file? "not-existing")))
  (is (soy-file? "resources/soy/example.soy"))
  (is (soy-file? (File. "resources/soy/example.soy"))))

(deftest test-soy-file-seq
  (is (empty? (soy-file-seq "not-existing")))
  (let [files (soy-file-seq "resources/soy")]
    (is (seq? files))
    (is (every? #(isa? (class %) File) files))))

(deftest test-soy-uri-seq
  (is (empty? (soy-uri-seq "not-existing")))
  (let [uris (soy-uri-seq "resources/soy")]
    (is (seq? uris))
    (is (every? #(isa? (class %) URI) uris))))

