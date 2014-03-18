using System;
using System.Collections.Generic;
using System.Text;
using DeployLX.Licensing.Management.v4;
using DeployLX.Licensing.v4;
using System.IO;

namespace DeploylxInterface
{
    class License
    {
        LicenseKey keyfile;

        public License()
        {
            keyfile = new LicenseKey("Keys/iCalabo.lsk");
        }

        public License(string fileAddress)
        {
           keyfile = new LicenseKey(fileAddress);
        }

        public License(Stream fileStream)
        {
            keyfile = new LicenseKey(fileStream);
        }

        public string GenerateExtensionNumber()
        {
            throw new NotImplementedException();
        }

        public string[] GenerateSerialNumberList(int startSeed,int endSeed)
        {
            String[] list = new string[endSeed - startSeed];
            int index = 0;
            for (int i = startSeed; i < endSeed - startSeed; i++)
            {
                list[index] = GenerateSerialNumberWithDefaults(i);
                index++;
            }
            return list;
        }

        public string GenerateSerialNumberWithDefaults(int seed)
        {
            return keyfile.MakeSerialNumber("STD-", seed, SerialNumberFlags.None, -1, 0, -1, 0, null, DefaultCharSet, CodeAlgorithm.SerialNumber);
        }

        public string GenerateSerialNumber(string prefix,int seed, int extendLimitOrdinal1,int extendLimitValue1,int extendLimitOrdinal2,int extendLimitValue2,int[] groupSizes,string characterSet)
        {
            return keyfile.MakeSerialNumber(prefix, seed, SerialNumberFlags.None, extendLimitOrdinal1, extendLimitValue1, extendLimitOrdinal2, extendLimitValue2, groupSizes, characterSet, CodeAlgorithm.SerialNumber);
        }

        public string GenerateActivationCodeWithDefaultPrefix(string serialNumber, string hash, int refid, string expireDate)
        {
            String prefix = "STD-";
            return GenerateActivationCodeWithDefaultCharSet(prefix, serialNumber, hash, refid, expireDate);
        }

        public string GenerateActivationCodeWithDefaultCharSet(string prefix, string serialNumber, string hash, int refid, string expireDate)
        {
            String charSet = DefaultCharSet;
            return GenerateActivationCode(prefix, serialNumber, hash, refid, expireDate,charSet);
        }

        public string GenerateActivationCode(string prefix,string serialNumber,string hash,int refid,string expireDate,string charSet)
        {
            DateTime expire;
            try 
	        {	        
		        expire = DateTime.Parse(expireDate);
	        }
	        catch (Exception)
	        {
                expire = DateTime.Now.AddYears(20);//Default expire date is 20 years from issue date
	        }
            String activationCode = keyfile.MakeActivationCode(prefix,serialNumber,hash,refid,expire,charSet,CodeAlgorithm.ActivationCode);
            return activationCode;
        }

        public const string DefaultCharSet = "U9VWT2FG3Q7RS0AC1DEYMNX6P8HJ4KL5";

        static void Main(string[] args)
        {
            License lic = new License();
            Console.WriteLine(lic.GenerateSerialNumberWithDefaults(12));
            Console.ReadKey();
        }
    }
}
