df <- read.table("characteristics.txt", header=TRUE)

png("T_1000.png")
attach(mtcars)
par(mfrow=c(2,2))
plot(df$t, df$T, main="T(t)", xlab="t", ylab="T")
plot(df$t, df$H, main="H(t)", xlab="t", ylab="H")
plot(df$t, df$E, main="E(t)", xlab="t", ylab="E")
plot(df$t, df$V, main="V(t)", xlab="t", ylab="V")
dev.off()



