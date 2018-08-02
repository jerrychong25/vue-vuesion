package my.com.codeplay.loancalculator;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import static java.lang.Math.pow;

public class CalculatorActivity extends AppCompatActivity {

    private EditText etLoanAmount, etDownPayment, etTerm, etAnnualInterestRate;
    private TextView tvMonthlyPayment, tvTotalRepayment, tvTotalInterest, tvAverageMonthlyInterest;
    public static final String MyPREFERENCES = "MyPrefs" ;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        sp = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        etLoanAmount = (EditText) findViewById(R.id.loan_amount);
        etDownPayment = (EditText) findViewById(R.id.down_payment);
        etTerm = (EditText) findViewById(R.id.term);
        etAnnualInterestRate = (EditText) findViewById(R.id.annual_interest_rate);

        String previous_loanAmount = sp.getString("LoanAmount", "");
        String previous_downPayment = sp.getString("DownPayment", "");
        String previous_interestRate = sp.getString("AnnualInterestRate", "");
        String previous_term = sp.getString("Term", "");

        etLoanAmount.setText(previous_loanAmount);
        etDownPayment.setText(previous_downPayment);
        etAnnualInterestRate.setText(previous_interestRate);
        etTerm.setText(previous_term);

        tvMonthlyPayment = (TextView) findViewById(R.id.monthly_repayment);
        tvTotalRepayment = (TextView) findViewById(R.id.total_repayment);
        tvTotalInterest = (TextView) findViewById(R.id.total_interest);
        tvAverageMonthlyInterest = (TextView) findViewById(R.id.average_monthly_interest);
    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.button_calculate:
                Log.d("Check", "Calculate Button Clicked!");
                calculate();
                break;
            case R.id.button_reset:
                Log.d("Check", "Reset Button Clicked!");
                reset();
                break;
        }
    }

    private void calculate () {
        String amount = etLoanAmount.getText().toString();
        String downPayment = etDownPayment.getText().toString();
        String interestRate = etAnnualInterestRate.getText().toString();
        String term = etTerm.getText().toString();

        double loanAmount = Double.parseDouble(amount) - Double.parseDouble(downPayment);
        double interest = Double.parseDouble(interestRate)/12/100;
        double noOfMonth = (Integer.parseInt(term) * 12);

        if(noOfMonth > 0) {
            double monthlyRepayment = loanAmount * (interest + (interest/(pow((1+interest), noOfMonth) - 1)));
            double totalRepayment = monthlyRepayment * noOfMonth;
            double totalInterest = totalRepayment - loanAmount;
            double monthlyInterest = totalInterest / noOfMonth;

            tvMonthlyPayment.setText(String.valueOf(monthlyRepayment));
            tvTotalRepayment.setText(String.valueOf(totalRepayment));
            tvTotalInterest.setText(String.valueOf(totalInterest));
            tvAverageMonthlyInterest.setText(String.valueOf(monthlyInterest));
        }

        SharedPreferences.Editor editor = sp.edit();

        editor.putString("LoanAmount", amount);
        editor.putString("DownPayment", downPayment);
        editor.putString("AnnualInterestRate", interestRate);
        editor.putString("Term", term);

        editor.apply();
        editor.commit();
    }

    private void reset(){
        etLoanAmount.setText("");
        etDownPayment.setText("");
        etTerm.setText("");
        etAnnualInterestRate.setText("");

        tvMonthlyPayment.setText(R.string.default_result);
        tvTotalRepayment.setText(R.string.default_result);
        tvTotalInterest.setText(R.string.default_result);
        tvAverageMonthlyInterest.setText(R.string.default_result);
    }
}
