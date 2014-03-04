using System;
using System.Collections.Generic;
using System.Text;

namespace LicenseJNI
{
    public class License
    {
        public int Validate(bool silent)
        {
            try
            {
                var helper = new LicenseHelper();
                helper.Required();
                if (helper.IsTrial)
                    return 12;
                else if (helper.IsActivated)
                    return 13;
                else
                    return 10;
            }
            catch (Exception)
            {
                return -1;
            }
        }

        public static void Main(string[] args)
        {
            new License().Validate(false);
        }
    }
}
