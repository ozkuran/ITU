; Image processing with algebraic operations
; NASM code for image processing Assignment of BLG 413E, ITU, Fall 2013
; Written by Mahmut Ali OZKURAN 040000815 07/10/2013
; use MAKE to compile the related source codes


global brightness, contrast, average, grow, shrink, blur, invert, edge, atleast, clear

extern printf

section .data
	fmt:    db "ebp+8=%X, ebp+12=%d, ebp+16=%d, ebp+20=%d", 10, 0 ; The printf format, "\n",'0'
	sngx:	db "var=%X", 10, 0
	sngd:	db "var=%d", 10, 0


section .bss
	height resd 1
	width  resd 1
	heightl resd 1
	widthl resd 1
	val resb 1
	itemcount resd 1

section .text
; set brightness of the image
brightness: ;void brightness(unsigned char *i1, int w, int h, int val);
	push ebp          ;save the old base pointer value
	mov  ebp,esp      ;base pointer <- stack pointer


	mov eax, [ebp+16]		; find imagesize - load image height
	mov ebx, [ebp+12]		; find imagesize - load image width
	mul ebx					; find imagesize - multiply height and width
	mov esi, [ebp+8]    	; image
	mov ecx, eax     		; imagesize
	mov ebx, [ebp+20]		; brightness adjust value loaded



brightness_loop:
	xor eax, eax
	mov al, [esi+ecx]
	add ax, bx
	cmp ax, 255
	jg brightness_max
	cmp ax, 0	
	jl brightness_min
	jmp brightness_end

brightness_max:
	mov ax, 255
	jmp brightness_end

brightness_min:
	mov ax, 0

brightness_end:
	mov [esi+ecx], al
	sub ecx, 1
	jnz brightness_loop

    pop  ebp          ;restore base pointer
    ret               ;jump to return address


contrast:
    push ebp          		;save the old base pointer value
    mov  ebp,esp      		;base pointer <- stack pointer

	mov eax, [ebp+16]		; find imagesize - load image height
	mov ebx, [ebp+12]		; find imagesize - load image width
	mul ebx					; find imagesize - multiply height and width
	mov esi, [ebp+8]    	; image
	mov ecx, eax     		; imagesize
	mov ebx, [ebp+20]		; contrast adjust value loaded


contrast_loop:
	xor eax, eax
	mov al, [esi+ecx]
	mul ebx
	shr eax, 6
	cmp eax, 255
	jg contrast_max
	cmp ax, 0
	jl contrast_min
	jmp contrast_end

contrast_max:
	mov ax, 255
	jmp contrast_end

contrast_min:
	mov ax, 0

contrast_end:
	mov [esi+ecx], al
	sub ecx, 1
	jnz contrast_loop

    mov  ecx,[ebp+8]  		;first argument
    mov  edx,[ebp+12] 		;second argument
    xor  eax,eax      		;clear eax (used for returning the result)

    pop  ebp          		;restore base pointer
    ret               		;jump to return address

average:
    push ebp          		;save the old base pointer value
    mov  ebp,esp      		;base pointer <- stack pointer

	mov eax, [ebp+20]		; find imagesize - load image height
	mov ebx, [ebp+16]		; find imagesize - load image width
	mul ebx					; find imagesize - multiply height and width
	mov ecx, eax     		; imagesize loaded to ecx
	mov esi, [ebp+8]		; loading 1st image address to ESI register
	mov ebx, [ebp+12]		; loading 2nd image address to EBX register

average_loop:
	xor eax, eax
	xor edx, edx
	mov al, [esi+ecx]
	mov dl, [ebx+ecx]
	add eax, edx
	shr eax, 1
	mov [esi+ecx], al
	sub ecx, 1
	jnz average_loop

    pop  ebp          		;restore base pointer
    ret               		;jump to return address

grow:						;grow(unsigned char *image, unsigned char *newimage, int width, int height);
    push ebp          		;save the old base pointer value
    mov  ebp,esp      		;base pointer <- stack pointer

	mov eax, [ebp+20]		; find imagesize - load image height
	mov ebx, [ebp+16]		; find imagesize - load image width
	mul ebx					; find imagesize - multiply height and width
	mov ecx, eax     		; imagesize loaded to ecx
	mov esi, [ebp+8]		; loading 1st image address to ESI register
	;add esi, ecx
	mov edi, [ebp+12]		; loading 2nd image address to EDI register
	mov eax, [ebp+20]		; load image height
	mov [height], eax		; load image height
	mov eax, [ebp+16]		; load image width
	mov [width], eax		; load image width

	mov eax, ecx
	shl eax, 2
	mov ebx, eax

grow_loop:

	;push ecx

	;push dword [height]
	;push dword sngd	; address of ctrl string
	;call printf		; Call C function	
	;add	esp, 8		; pop stack 5 push times 4 bytes




	;push eax
	;push dword sngd	; address of ctrl string
	;call printf		; Call C function	
	;add	esp, 8		; pop stack 5 push times 4 bytes


	;pop ecx

	;push ecx
	;push dword sngd	; address of ctrl string
	;call printf		; Call C function	
	;add	esp, 8		; pop stack 5 push times 4 bytes

	xor eax, eax					; clear eax
	mov al, [esi+ecx]		; load original image value to al
	;mov [colorval], al


	mov [edi+ebx], al
	dec ebx
	mov [edi+ebx], al

	;sub ebx, dword [width]
	;sub ebx, dword [width]
	mov [edi+ebx], al
	inc ebx
	mov [edi+ebx], al
	;add ebx, dword [width]
	;add ebx, dword [width]
	dec ebx
	dec ebx

	sub ecx, 1
	;push ecx
	jnz grow_loop

    pop  ebp          ;restore base pointer
    ret               ;jump to return address

shrink:
    push ebp          ;save the old base pointer value
    mov  ebp,esp      ;base pointer <- stack pointer

	mov eax, [ebp+20]		; find imagesize - load image height
	mov ebx, [ebp+16]		; find imagesize - load image width
	mul ebx					; find imagesize - multiply height and width
	mov ecx, eax     		; imagesize loaded to ecx
	mov esi, [ebp+8]		; loading 1st image address to ESI register
	mov edi, [ebp+12]		; loading 2nd image address to EDI register
	mov eax, [ebp+20]		; load image height
	mov [height], eax		; load image height
	mov eax, [ebp+16]		; load image width
	mov [width], eax		; load image width

	mov eax, ecx
	shr eax, 2
	mov ebx, eax

	xor eax, eax
	mov eax, [height]
	mov [heightl], eax

	xor eax, eax
	mov eax, [width]
	mov [widthl], eax



shrink_loop:
	
	xor eax, eax
	mov eax, [width]
	mov [widthl], eax
	
	cmp dword [heightl],0
	sub ecx, [width]
	jg shrink_line
	jmp shrink_end

shrink_line:
	xor eax, eax			; clear eax
	mov al, [esi+ecx]		; load original image value to al

	mov [edi+ebx], al
	dec ebx

	sub ecx, 2
	dec dword [widthl]
	dec dword [widthl]
	cmp dword [widthl], 0
	jg shrink_line
	dec dword [heightl]
	dec dword [heightl]
	jmp shrink_loop

shrink_end:
    pop  ebp          ;restore base pointer
    ret               ;jump to return address


blur:
    push ebp          ;save the old base pointer value
    mov  ebp,esp      ;base pointer <- stack pointer

	mov eax, [ebp+16]		; find imagesize - load image height
	mov ebx, [ebp+12]		; find imagesize - load image width
	mul ebx				; find imagesize - multiply height and width
	mov ecx, eax     		; imagesize loaded to ecx
	mov esi, [ebp+8]		; loading 1st image address to ESI register
	mov eax, [ebp+16]		; load image height
	mov [height], eax		; load image height
	mov eax, [ebp+12]		; load image width
	mov [width], eax		; load image width
	mov [widthl], eax		; load image width
	
blur_loop:
	xor eax, eax
	xor ebx, ebx

	mov al, [esi+ecx]
	add ebx, eax
	dec ecx

	mov al, [esi+ecx]
	add ebx, eax
	sub ecx, [width]
	cmp ecx, 0
	jl blur_end

	mov al, [esi+ecx]
	add ebx, eax
	inc ecx
	mov al, [esi+ecx]
	add ebx, eax
	shr ebx, 2


	mov [esi+ecx], bl
	dec ecx
	mov [esi+ecx], bl
	add ecx, [width]
	mov [esi+ecx], bl
	inc ecx
	mov [esi+ecx], bl
	;add ecx, [width]


	dec ecx
	jz blur_end
	dec ecx
	jz blur_end
	
	dec dword [widthl]
	cmp dword [widthl],0
	jg blur_loop
	sub ecx, dword [width]
	mov eax, [width]
	mov [widthl], eax
	jmp blur_loop	

blur_end:

    pop  ebp          ;restore base pointer
    ret               ;jump to return address

invert:
    push ebp          ;save the old base pointer value
    mov  ebp,esp      ;base pointer <- stack pointer

	mov eax, [ebp+16]		; find imagesize - load image height
	mov ebx, [ebp+12]		; find imagesize - load image width
	mul ebx					; find imagesize - multiply height and width
	mov ecx, eax     		; imagesize loaded to ecx
	mov esi, [ebp+8]		; loading 1st image address to ESI register



invert_loop:
	xor eax, eax
	xor ebx, ebx
	mov bl, [esi+ecx]
	mov al, 255
	sub al, bl

	mov [esi+ecx], al
	sub ecx, 1
	jnz invert_loop

    pop  ebp          ;restore base pointer
    ret               ;jump to return address

edge:
    push ebp          ;save the old base pointer value
    mov  ebp,esp      ;base pointer <- stack pointer

	mov eax, [ebp+16]		; find imagesize - load image height
	mov ebx, [ebp+12]		; find imagesize - load image width
	mul ebx					; find imagesize - multiply height and width
	mov ecx, eax     		; imagesize loaded to ecx
	mov esi, [ebp+8]		; loading 1st image address to ESI register

	mov eax, [ebp+16]		; load image height
	mov [height], eax		; load image height
	mov eax, [ebp+12]		; load image width
	mov [width], eax		; load image width

	mov eax, [ebp+16]		; load image height
	mov [heightl], eax		; load image height to variable
	mov eax, [ebp+12]		; load image width
	mov [widthl], eax		; load image width to variable


edge_loop:
	cmp ecx, [width]
	jle edge_top_line
	xor eax, eax
	xor ebx, ebx
	mov bl, [esi+ecx]
	sub ecx, [width]
	mov al, [esi+ecx]
	add ecx, [width]
	cmp al, bl
	jle edge_darker

edge_brighter:
	mov [esi+ecx], byte 255
	sub ecx, 1
	jmp edge_loop

edge_darker:
	mov [esi+ecx], byte 0
	sub ecx, 1
	jmp edge_loop


edge_top_line:
	mov [esi+ecx], byte 0

edge_end:
	sub ecx, 1
	jnz edge_top_line

    pop  ebp          ;restore base pointer
    ret               ;jump to return address

atleast:
    push ebp          ;save the old base pointer value
    mov  ebp,esp      ;base pointer <- stack pointer

	mov eax, [ebp+16]		; find imagesize - load image height
	mov ebx, [ebp+12]		; find imagesize - load image width
	mul ebx					; find imagesize - multiply height and width
	mov esi, [ebp+8]    	; image
	mov ecx, eax     		; imagesize
	mov ebx, [ebp+20]		; parameter value loaded
	mov [val], bl
	xor eax, eax
	mov [itemcount], eax
	
atleast_loop:
	xor eax, eax
	mov al, [esi+ecx]
	cmp eax, ebx
	jl atleast_nocount
	inc dword [itemcount]

atleast_nocount:
	sub ecx, 1
	jnz atleast_loop

	mov eax, [itemcount]
	mov [ebp+20], eax

    pop  ebp          ;restore base pointer
    ret               ;jump to return address

clear:
    push ebp          ;save the old base pointer value
    mov  ebp,esp      ;base pointer <- stack pointer


	mov eax, [ebp+16]		; find imagesize - load image height
	mov ebx, [ebp+12]		; find imagesize - load image width
	mul ebx					; find imagesize - multiply height and width
	mov esi, [ebp+8]    	; image location
	mov ecx, eax     		; imagesize
	mov ebx, [ebp+20]		; parameter value loaded
	mov [val], bl
	
clear_loop:
	mov [esi+ecx], bl
	sub ecx, 1
	jnz clear_loop

    pop  ebp          ;restore base pointer
    ret               ;jump to return address

