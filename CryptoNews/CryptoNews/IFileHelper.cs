using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CryptoNews
{
    public interface IFileHelper
    {
        string GetLocalFilePath(string filename);
    }
}
