(ns axon.edn.serializer.reader.test)
(defrecord TestRecordWithNormalClassPath [a b])

(ns axon.edn.serializer.reader-test
  (:require [clojure.test :refer :all]
            [axon.edn.serializer.reader :as r]

            )
  (:import [ axon.edn.serializer.reader.test TestRecordWithNormalClassPath])
  (:use midje.sweet))

(defrecord TestRecord [a b])

(fact "should be able wrap record"
      (let [record (TestRecordWithNormalClassPath. 1 2)
            record_str (prn-str record )]
        (r/read-string  record_str) => record))


(fact "should be able to handle record with class path - in it"
      (let [record (TestRecord. 1 2)
            record_str (prn-str record )]
        (r/read-string  record_str) => record))


(fact "should be able to handle java object- in it"
      (let [record (Object.)
            record_str (prn-str record )]
        (r/read-string  record_str) => record))
