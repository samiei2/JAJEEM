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
                //helper.Deactivate();
                //helper.ReShowRegistrationForm();
                helper.Required(true);
                if (helper.IsTrial)
                    return 12;
                else if (helper.IsActivated)
                    return 13;
                else if (helper.IsActivation)
                    return 14;
                else
                    return 10;
            }
            catch (Exception e)
            {
                global::System.Windows.Forms.MessageBox.Show(e.Message);
                return -1;
            }
        }

        public static void Main(string[] args)
        {
            new License().Validate(true);
            Console.ReadKey();
        }
    }
}
