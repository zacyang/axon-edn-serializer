(ns axon.edn.serializer.reader
  (:require [clojure.edn :as edn]))

(defn import-by-name [fqn]
  (.importClass (the-ns *ns*)
                (clojure.lang.RT/classForName (str fqn))))

;;(def generate->fn (memoize gen-record))
(defn retrieve-legit-cp
  [s]
  (clojure.string/replace
   (clojure.string/replace s  #"\.\w+$" "")
   #"_" "-"))

(defn gen-record
  [tag]
  (let [tag_str           (str tag)
        type_name         (last (clojure.string/split tag_str #"\."))
        namespace_literal (retrieve-legit-cp tag_str)
        fqn (str namespace_literal "/map->" type_name  )]
    (resolve (symbol fqn))))

(defn- create-record
  [tag val]
  ((gen-record tag) val))

(defn read-string
  [s]
  (edn/read-string {:default create-record} s))
