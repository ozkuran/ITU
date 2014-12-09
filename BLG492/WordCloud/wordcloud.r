library(wordcloud)
library(RColorBrewer)

tags <- read.csv("tags.csv", header = TRUE, sep = ",")

png("wc.png")
pal = brewer.pal(9,"Blues")
wordcloud(words = tags$tag, 
          freq = tags$count, 
          colors=brewer.pal(6,"Dark2"),random.order=FALSE)
dev.off()



