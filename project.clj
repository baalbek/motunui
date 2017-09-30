(defproject motunui "1.0"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [
		[org.springframework/spring-core "4.2.3.RELEASE"]
		[org.springframework/spring-context "4.2.3.RELEASE"]
		[org.springframework/spring-aop "4.2.3.RELEASE"]
		[org.clojure/clojure "1.8.0"]
		[org.mybatis/mybatis "3.4.2"]
		[junit/junit "4.12"]
		[log4j/log4j "1.2.17"]

    ]
  ;:main ^:skip-aot harborview.webapp
  ;:compile
  :global-vars {*warn-on-reflection* true}
  :target-path "target"
  :source-paths ["src/clojure"]
  :test-paths ["test/clojure" "dist" "test/resources"]
  :java-source-paths ["src/java" "test/java"]
  :javac-options     ["-target" "1.8" "-source" "1.8"]
  :aot :all
  ;:test {:resource-paths ["test/resources" "dist"]}
  :resource-paths [
		"/home/rcs/opt/java/tongariki/target/tongariki-1.0.jar"
		"/home/rcs/opt/java/oahu/build/libs/oahu-1.1.jar"
		"/home/rcs/opt/java/ranoraraku/build/libs/ranoraraku-1.1.jar"
		"/home/rcs/opt/java/vega/build/libs/vega-1.2.jar"

                   ]
  :profiles {:uberjar {:aot :all}})
