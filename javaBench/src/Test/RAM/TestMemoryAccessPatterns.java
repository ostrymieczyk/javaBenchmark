package Test.RAM;

import Helper.ResultController;
import Helper.Timer;

public class TestMemoryAccessPatterns implements Runnable
{
    private static final int LONG_SIZE = 8;
    private static final int PAGE_SIZE = 2 * 1024 * 1024;
    private static final int ONE_GIG = 1024 * 1024 * 1024;
    private static final long TWO_GIG = 2L * ONE_GIG;

    private static final int ARRAY_SIZE = (int)(TWO_GIG / LONG_SIZE);
    private static final int WORDS_PER_PAGE = PAGE_SIZE / LONG_SIZE;

    private static final int ARRAY_MASK = ARRAY_SIZE - 1;
    private static final int PAGE_MASK = WORDS_PER_PAGE - 1;

    private static final int PRIME_INC = 514229;

    private static final long[] memory = new long[ARRAY_SIZE];

    static long TOTAL_TIME = 0;

    static
    {
        for (int i = 0; i < ARRAY_SIZE; i++)
        {
            memory[i] = 777;
        }
    }

    public enum StrideType
    {
        LINEAR_WALK
                {
                    public int next(final int pageOffset, final int wordOffset, final int pos)
                    {
                        return (pos + 1) & ARRAY_MASK;
                    }
                },

        RANDOM_PAGE_WALK
                {
                    public int next(final int pageOffset, final int wordOffset, final int pos)
                    {
                        return pageOffset + ((pos + PRIME_INC) & PAGE_MASK);
                    }
                },

        RANDOM_HEAP_WALK
                {
                    public int next(final int pageOffset, final int wordOffset, final int pos)
                    {
                        return (pos + PRIME_INC) & ARRAY_MASK;
                    }
                };

        public abstract int next(int pageOffset, int wordOffset, int pos);
    }

    public void run()
    {
        System.out.println(ARRAY_SIZE);
        for (int i = 0; i < 2; i++)
        {
            TOTAL_TIME += perfTest(StrideType.LINEAR_WALK);
        }
        ResultController.setRamLinearWalkReslut(TOTAL_TIME);
        TOTAL_TIME = 0;
        for (int i = 0; i < 2; i++)
        {
            TOTAL_TIME += perfTest(StrideType.RANDOM_HEAP_WALK);
        }ResultController.setRamRandomPageWalkReslut(TOTAL_TIME);
        TOTAL_TIME = 0;
        for (int i = 0; i < 2; i++)
        {
            TOTAL_TIME += perfTest(StrideType.RANDOM_PAGE_WALK);
        }ResultController.setRamRandomHeapWalkReslut(TOTAL_TIME);
        TOTAL_TIME = 0;
    }

    private static long perfTest(final StrideType strideType)
    {
        Timer t = new Timer();

        int pos = -1;
        long result = 0;
        for (int pageOffset = 0; pageOffset < ARRAY_SIZE; pageOffset += WORDS_PER_PAGE)
        {
            for (int wordOffset = pageOffset, limit = pageOffset + WORDS_PER_PAGE;
                 wordOffset < limit;
                 wordOffset++)
            {
                pos = strideType.next(pageOffset, wordOffset, pos);
                result += memory[pos];
            }
        }

        final long duration = t.check();

        if (208574349312L != result)
        {
            throw new IllegalStateException();
        }


        return duration;
    }
}