(ns happy-birthday.core-test
  (:use clojure.test
        happy-birthday.core))

(deftest main-test 
  (testing "The program runs and exits successfully"
    (is (= (-main) 0))))
