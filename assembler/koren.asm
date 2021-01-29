%macro pushd 0
    push edx
    push ecx
    push ebx
    push eax
%endmacro

%macro popd 0
    pop eax
    pop ebx
    pop ecx
    pop edx
%endmacro

%macro print 2
    pushd
    mov edx, %1
    mov ecx, %2
    mov ebx, 1
    mov eax, 4
    int 0x80

    popd
%endmacro

%macro dprint 1
    pushd
    mov ecx, 10
    mov bx, 0 
    mov eax, %1
    
    %%_divide:
        mov edx, 0
        div ecx
        push dx
        inc bx
        test eax, eax
        jnz %%_divide
    
    %%_digit:
        pop ax
        add ax, '0'
        mov [count], ax
        print 1, count
        dec bx
        cmp bx, 0
        jg %%_digit
    
    popd
%endmacro

%macro go 2
    push ebx
    push ecx
    push edx
    mov edx, 0
    mov ecx, %2
    
    push ecx
    mov eax, %1
    div ecx
    pop edx
    
    add eax, edx
    mov edx, 0
    mov ecx, 2
    div ecx
    
    pop edx
    pop ecx
    pop ebx
%endmacro

section .text

global _start

_start:
    mov eax, [my]
    mov ecx, 2
    div ecx 
    mov [x], eax
    go [my], eax
    mov [y], eax
    
_repeat:
    mov eax, [x]
    mov ecx, [y]
    sub eax, ecx 
    cmp eax, 1 
    jl _end  ; <
    
    mov eax, [y]
    mov [x], eax
    go [my], [x]
    mov [y], eax
    jmp _repeat
    
_end:    
    dprint [y]
    
    print nlen, n
    print len, message
    print nlen, n
    
    mov     eax, 1
    int     0x80

section.data
    my DD 23
    
    message DB "Done"
    len EQU $ - message
    
    n DB 0xA, 0xD
    nlen EQU $ - n
    
section .bss
    count resd 1
    x resd 1 
    y resd 1
