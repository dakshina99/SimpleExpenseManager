/*
 * Copyright 2015 Department of Computer Science and Engineering, University of Moratuwa.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *                  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package lk.ac.mrt.cse.dbs.simpleexpensemanager;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Test;
import org.junit.BeforeClass;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.ExpenseManager;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.PersistentDemoExpenseManager;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.exception.ExpenseManagerException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest {
    private static ExpenseManager testExpenceManager;


    @BeforeClass
    public static void setupClient() {
        try{
            Context cont = ApplicationProvider.getApplicationContext();
            testExpenceManager = new PersistentDemoExpenseManager(cont);
        }catch (ExpenseManagerException e){ }
    }

    @Test
    public void testAddAccount(){
        testExpenceManager.addAccount("404a", "Bank X", "peter parker", 1000.0);
        assertTrue(testExpenceManager.getAccountNumbersList().contains("404a"));
    }

    @Test
    public void testTransaction(){
        try{

            testExpenceManager.updateAccountBalance("12345A",10, 4, 2022, ExpenseType.EXPENSE, "500");

            int sizeOfLogsArr = testExpenceManager.getTransactionLogs().size();
            Transaction last_transaction = testExpenceManager.getTransactionLogs().get(sizeOfLogsArr - 1);

            SimpleDateFormat formatter = new SimpleDateFormat("dd,MM,yyyy");
            String strDate= formatter.format(last_transaction.getDate());

            String[] testArr1 = {"12345A","500.0"};
            String[] testArr2 = {last_transaction.getAccountNo(), String.valueOf(last_transaction.getAmount())};

            assertEquals("10,05,2022", strDate);
            assertEquals(ExpenseType.EXPENSE, last_transaction.getExpenseType());
            assertArrayEquals(testArr1, testArr2);

        }catch (InvalidAccountException e){

        }

    }


}