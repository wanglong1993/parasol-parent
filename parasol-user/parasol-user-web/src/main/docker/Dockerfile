FROM java:6
# time zone
RUN echo "Asia/Chongqing" > /etc/timezone && dpkg-reconfigure -f noninteractive tzdata
RUN mkdir /parasol-user-web222
RUN mkdir /parasol-user-web222/logs
ADD app.jar /parasol-user-web222/parasol-user-web222.jar
ADD runboot.sh /parasol-user-web222/
RUN bash -c 'touch /parasol-user-web222/parasol-user-web222.jar'
WORKDIR /parasol-user-web222
RUN chmod a+x runboot.sh
CMD /parasol-user-web222/runboot.sh