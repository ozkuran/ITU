CC=gcc
ASM=nasm

all:
	$(ASM) -f elf32 -g bitmap.asm -o bmpasm.o
	$(CC) -c bitmap.c -g -o bmpc.o
	$(CC) bmpasm.o bmpc.o -g -o bmp 
