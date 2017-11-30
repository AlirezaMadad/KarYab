using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace KarYab.Models.ViewModel
{
    public class ResumeViewModel
    {
        long HumanId;
        public string NAME { get; set; }
        public byte GENDER { get; set; }
        public short USAGE { get; set; }
        //public byte[] Image { get; set; }
        public List<EXPERIENCE> Experiences { get; set; }
        public List<EDUCATON> Educations { get; set; }
        public List<SKILL> Skills { get; set; }
        public List<ADRESS> Adresses { get; set; }
        public List<CELLPHONE> Cellphones { get; set; }
        public List<PHONE> Phones { get; set; }
        public List<MAILADRESS> Emails { get; set; }

    }
}