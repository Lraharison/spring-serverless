package org.cyranno.springserverless.sum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SumRequest {
    private int a;
    private int b;
}
