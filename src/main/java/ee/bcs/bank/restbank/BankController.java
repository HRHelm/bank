package ee.bcs.bank.restbank;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/solution")
public class BankController {

    public static Bank bank = new Bank();

    @Resource
    private AccountService accountService;

    @Resource
    private TransactionService transactionService;

    @Resource
    private BankService bankService;

    //  et saada bank objektist ülevaade, siis loo uus controlleri endpoint    /bank
    //  meetodi nimeks pane                                                         getBank()

    @GetMapping("/bank")
    public Bank getBank() {
        return bank;
    }

//      et saada üks accounts JSON'i näidis,
//      siis loo uus controlleri endpoint                                           /example/account
//      meetodi nimeks pane                                                         getExampleAccount()
//      loo accountService alla uus teenus                                          createExampleAccount()

    @GetMapping("/example/account")
    public AccountDto getExampleAccount() {
        return accountService.createExampleAccount();
    }

    //  et saada üks transaction JSON'i näidis,
    //  siis loo uus controlleri endpoint                                           /example/transaction
    //  meetodi nimeks pane                                                         getExampleTransaction()
    //  loo transactionService alla uus teenus                                          createExampleTransaction()

    @GetMapping("/example/transaction")
    public TransactionDto getExampleTransaction() {
        return transactionService.createExampleTransaction();
    }


//      Et lisada uus account, loo uus controlleri endpoint                    /new/account
//      võta RequestBodyst sisse accountDto objekt
//      loo bankService alla uus teenus                                             addAccountToBank()
//      ja lisa see konto bank accounts listi
//      teenus võiks tagastada RequestResult objekti koos koos loodava konto id ja transaktsiooni id'ga

    @PostMapping("/new/account")
    public RequestResult addAccountToBank(@RequestBody AccountDto accountDto) {
        return bankService.addAccountToBank(bank, accountDto);
    }

    @PostMapping("/new/transaction")
    public RequestResult addNewTransaction(@RequestBody TransactionDto transactionDto) {
        RequestResult requestResult = transactionService.addNewTransaction(bank, transactionDto);
        return requestResult;

    }

    @PostMapping("/receive/transaction")
    public RequestResult receiveNewTransaction(@RequestBody TransactionDto transactionDto) {
        RequestResult requestResult = transactionService.receiveNewTransaction(bank, transactionDto);
        return requestResult;
    }

    @PutMapping("/update/owner")
    public RequestResult updateOwnerDetails(@RequestBody AccountDto accountDto) {

        return accountService.updateOwnerDetails(bank.getAccounts(), accountDto);
    }

//    todo tehke endpoint millega saab kontot lukustada/avada. Kontrollige ID olemasolu

    @DeleteMapping("/delete/account")
    public RequestResult deleteAccount(@RequestParam int accountId) {


        return accountService.deleteAccount(bank.getAccounts(), accountId);
    }

    @PutMapping("/update/account/lock")
    public RequestResult UpdateAccountLock(@RequestParam int accountId) {

        return accountService.updateAccountLock(bank.getAccounts(), accountId);
    }


//    todo: Loo endpoint /bankstatement/by/lastname
}
