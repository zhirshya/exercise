#pragma once

namespace funcs
{
    constexpr size_t fibonacci_inner(size_t t1,size_t t2,size_t index,size_t n)
    {
        return n==index?t1+t2:fibonacci_inner(t2,t1+t2,index+1,n);
    }

    constexpr size_t fibonacci(size_t n)
    {
        return n<2?1:fibonacci_inner(0,1,1,n);
    }

    static_assert(fibonacci(0)==1,"fibonacci(0)");
    static_assert(fibonacci(1)==1,"fibonacci(1)");
    static_assert(fibonacci(2)==2,"fibonacci(2)");
    static_assert(fibonacci(3)==3,"fibonacci(3)");
    static_assert(fibonacci(4)==5,"fibonacci(4)");
    static_assert(fibonacci(5)==8,"fibonacci(5)");

}
