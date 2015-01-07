library(wordcloud)
library(RColorBrewer)

tags <- read.csv("tags.csv", header = TRUE, sep = ",")

png("wc.png", width=1024, height=640)
pal = brewer.pal(9,"Blues")
wordcloud(words = tags$tag, 
          freq = tags$count, 
          scale=c(16,1),colors=brewer.pal(6,"Dark2"),random.order=FALSE)
dev.off()



