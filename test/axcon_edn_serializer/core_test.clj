(ns axon.edn.serializer.core-test
  (:import [org.axonframework.serializer
            SimpleSerializedObject]
           [axon.edn.serializer.reader AAA])
  (:require [clojure.test :refer :all]
            [clojure.edn :as edn]
            [axon.edn.serializer.core :as core]
            [axon.edn.serializer.reader :as reader]

            )
  (:use midje.sweet))

(defrecord TestRecord [a b c])
(def ^:private simple_java_obj (Object.))
(tabular
 (fact "should get default map value"
       (let [serializer (core/create-edn-serializer)
             result     (.serialize serializer original_value (.getClass java.lang.String))
             data       (.getData result)]

         (instance? SimpleSerializedObject result) => true
         data => (prn-str original_value)))
 original_value
 {:key "value"}
 '(1 2)
 simple_java_obj
 "string"
 #{:a}
 :a
 (->TestRecord 1 2 3)
 (TestRecord. 1 2 3)
 (map->TestRecord {:a 1 :b 2 :c 3})

 (fact "should return true for supported representation"
       (let [serializer (core/create-edn-serializer)]
         (.canSerializeTo serializer clojure.lang.Keyword) => true)))

(fact "should return true for additional supported representations"
      )


(fact "should return false for unsupported representations"
      (let [serializer (core/create-edn-serializer)]
      ;;  false => true
        ))

(fact "should get deserialize"
      (let [ serializer     (core/create-edn-serializer)
            serialized_obj  (.serialize serializer (AAA. 1 2 3) (.getClass java.lang.String))
            deserializedObj (.deserialize serializer serialized_obj ) ]
        ;(.getContentType serialized_obj) => java.lang.String
        ;(.getData serialized_obj) => "#axcon_edn_serializer.core_test.TestRecord{:a 1, :b 2, :c 3}\n"
        ;(type deserializedObj)=> ""
        (.deserialize serializer serialized_obj ) => (AAA. 1 2 3)))
